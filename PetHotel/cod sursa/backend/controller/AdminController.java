package hotelanimale.is.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import hotelanimale.is.service.AdminService;
import hotelanimale.is.model.Location;
import hotelanimale.is.service.LocationService;
import hotelanimale.is.model.Admin;
import hotelanimale.is.model.Reservation;
import hotelanimale.is.service.ReservationService;
import hotelanimale.is.model.User;
import hotelanimale.is.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/admins")
public class AdminController {
    private final AdminService adminService;
    private final LocationService locationService;
    private final UserService userService;
    private final ReservationService reservationService;

    @Autowired
    public AdminController(AdminService adminService, LocationService locationService, UserService userService, ReservationService reservationService) {
        this.adminService = adminService;
        this.locationService = locationService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewAdmin(@RequestBody Map<String, String> payload) {
        try {
            // Extract fields from the payload
            String username = payload.get("username");
            String password = payload.get("password");
            String firstName = payload.get("firstName");
            String lastName = payload.get("lastName");
            String locationIdStr = payload.get("locationId");

            if (username == null || username.isEmpty() ||
                    password == null || password.isEmpty() ||
                    firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    locationIdStr == null || locationIdStr.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "All fields are required."));
            }

            if (userService.findUserByUserName(username) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Username already exists."));
            }

            int locationId;
            try {
                locationId = Integer.parseInt(locationIdStr);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid location ID."));
            }

            Location location = locationService.getLocation(locationId);
            if (location == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Location not found."));
            }

            User newUser = new User(username, password, "Admin");
            userService.addNewUser(newUser);

            Admin newAdmin = new Admin(firstName, lastName, location, newUser);
            adminService.addAmin(newAdmin);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Admin added successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add admin.", "details", e.getMessage()));
        }
    }

    @DeleteMapping(path = "{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable int adminId) {
        try {
            adminService.deleteAdmin(adminId);
            return ResponseEntity.ok("Admin deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error deleting admin: " + e.getMessage());
        }
    }

    @GetMapping(path = "/byUserId")
    public ResponseEntity<?> getAdminByUserId(@RequestParam int userId) {
        try {
            Admin admin = adminService.getAdminByUserId(userId);
            if (admin != null) {
                return ResponseEntity.ok(admin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found for the given user ID.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching admin by user ID: " + e.getMessage());
        }
    }

    @GetMapping(path = "/byUsername")
    public ResponseEntity<?> getAdminByUsername(@RequestParam String username) {
        try {
            Admin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                return ResponseEntity.ok(admin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found for the given username.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching admin by username: " + e.getMessage());
        }
    }

    @GetMapping(path = "/byLocation")
    public ResponseEntity<?> getAdminsByLocation(@RequestParam int locationId) {
        try {
            List<Admin> admins = adminService.getAdminsByLocationId(locationId);
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching admins by location: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteAdminByUserId")
    public ResponseEntity<?> deleteAdminByUserId(@RequestParam int userId) {
        try {
            adminService.deleteAdminByUserId(userId);
            return ResponseEntity.ok("Admin deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting admin: " + e.getMessage());
        }
    }

    @PutMapping(path = "/{adminId}/details")
    public ResponseEntity<?> updateAdminDetails(
            @PathVariable int adminId,
            @RequestBody Admin adminDetails) {
        try {
            adminService.updateAdmin(adminId, adminDetails.getFirstName(), adminDetails.getLastName());
            return ResponseEntity.ok(Map.of("message", "Admin details updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error updating admin details: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/reservations")
    public ResponseEntity<?> getReservationsForAdminByUser(@PathVariable int userId) {
        Admin admin = adminService.getAdminByUserId(userId);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found for user ID: " + userId);
        }

        List<Reservation> reservations = reservationService.getReservationsByLocationId(admin.getLocation().getId());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{userId}/checkinout")
    public ResponseEntity<?> getReservationsForCheckInOut(@PathVariable int userId) {
        Admin admin = adminService.getAdminByUserId(userId);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found for user ID: " + userId);
        }

        LocalDate today = LocalDate.now();

        List<Reservation> allReservations = reservationService.getReservationsByLocationId(admin.getLocation().getId());
        List<Reservation> filteredReservations = allReservations.stream()
                .filter(reservation -> reservation.getCheckIn().equals(today) || reservation.getCheckOut().equals(today))
                .toList();

        if (filteredReservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservations found for today");
        }

        return ResponseEntity.ok(filteredReservations);
    }

}
