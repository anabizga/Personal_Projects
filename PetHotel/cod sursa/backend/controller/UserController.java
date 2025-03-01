package hotelanimale.is.controller;

import hotelanimale.is.model.Admin;
import hotelanimale.is.model.User;
import hotelanimale.is.service.AdminService;
import hotelanimale.is.model.Client;
import hotelanimale.is.service.ContactService;
import hotelanimale.is.model.Location;
import hotelanimale.is.service.LocationService;
import hotelanimale.is.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;
    private final ContactService contactService;
    private final ClientController clientService;
    private final AdminService adminService;
    private final LocationService locationService;

    @Autowired
    public UserController(UserService userService, ContactService contactService, ClientController clientService, AdminService adminService, LocationService locationService) {
        this.userService = userService;
        this.contactService = contactService;
        this.clientService = clientService;
        this.adminService = adminService;
        this.locationService = locationService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        try {
            User user = userService.getUser(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId+ " not found"));
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("userType", user.getUserType());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch user details", "details", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        System.out.println("Login attempt: " + loginUser.getUsername() + ", " + loginUser.getPassword());
        User user = userService.findUserByUserName(loginUser.getUsername());
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "userType", user.getUserType()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String password = payload.get("password");
            String firstName = payload.get("firstName");
            String lastName = payload.get("lastName");
            String birthDateString = payload.get("birthDate");

            if (userService.findUserByUserName(username) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Username already exists"));
            }

            User newUser = new User(username, password, "Client");
            userService.addNewUser(newUser);
            LocalDate birthDate = LocalDate.parse(birthDateString);
            Client newClient = new Client(firstName, lastName, birthDate, newUser);
            clientService.addNewClient(newClient);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User and client created successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to create user or client", "details", e.getMessage()));
        }
    }


    @PostMapping
    public void registerNewUser(@RequestBody User User) {
        userService.addNewUser(User);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) {
        Optional<User> optionalUser = userService.getUser(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = optionalUser.get();

        if (user.getUserType().equals("Client")) {
            clientService.deleteClientByUserId(userId);
        }
        if (user.getUserType().equals("Admin")) {
            adminService.deleteAdminByUserId(userId);
        }

        contactService.deleteContactsByUserId(userId);
        userService.deleteUser(userId);

        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping(path = "{userName}")
    public User getUserByName(@PathVariable String userName) {
        User User = userService.findUserByUserName(userName);
        System.out.println(User);
        return User;
    }

    @PutMapping(path = "{userId}/credentials")
    public ResponseEntity<?> updateUserCredentials(@PathVariable("userId") int userId, @RequestBody Map<String, String> payload) {
        try {
            String newUsername = payload.get("username");
            String newPassword = payload.get("password");

            Optional<User> optionalUser = userService.getUser(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            User user = optionalUser.get();

            if (newUsername != null && !newUsername.equals(user.getUsername())) {
                if (userService.findUserByUserName(newUsername) != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Username already exists"));
                }
                user.setUsername(newUsername);
            }

            if (newPassword != null) {
                user.setPassword(newPassword);
            }
            userService.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getUserType());

            return ResponseEntity.ok(Map.of("message", "User credentials updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to update user credentials", "details", e.getMessage()));
        }
    }

    @PutMapping(path = "{userId}/promoteToAdmin")
    public ResponseEntity<?> promoteToAdmin(@PathVariable("userId") int userId, @RequestParam int locationId) {
        try {
            System.out.println("Promoting user with ID: " + userId + " to admin at location ID: " + locationId);

            // Verificare utilizator
            Optional<User> optionalUser = userService.getUser(userId);
            if (optionalUser.isEmpty()) {
                System.out.println("User not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found."));
            }
            User user = optionalUser.get();
            System.out.println("Found user: " + user);

            if (!user.getUserType().equals("Client")) {
                System.out.println("User is not a client and cannot be promoted.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Only clients can be promoted to admins."));
            }

            Location location = locationService.getLocation(locationId);
            if (location == null) {
                System.out.println("Location not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Location not found."));
            }
            System.out.println("Found location: " + location);

            Client client = clientService.getClientByUserId(userId);
            if (client == null) {
                System.out.println("Client not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Client not found."));
            }
            System.out.println("Found client: " + client);

            user.setUserType("Admin");
            userService.updateUser(user.getId(), user.getUsername(), user.getPassword(), user.getUserType());
            System.out.println("User updated to Admin.");
            Admin admin = new Admin(client.getFirstName(), client.getLastName(), location, user);
            adminService.addAmin(admin);
            System.out.println("Admin created: " + admin);
            clientService.deleteClientByUserId(userId);
            System.out.println("Client deleted for user ID: " + userId);

            return ResponseEntity.ok(Map.of("message", "User promoted to admin successfully."));
        } catch (Exception e) {
            System.err.println("Error during promotion: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to promote user to admin.", "details", e.getMessage()));
        }
    }

}
