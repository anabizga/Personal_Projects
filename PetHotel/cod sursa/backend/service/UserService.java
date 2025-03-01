package hotelanimale.is.service;

import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }

    public void addNewUser(User user) {
        Optional<User> u = userRepository.findByUserName(user.getUsername());
        if (u.isPresent()) {
            throw new IllegalArgumentException("This user already exists");
        }
        System.out.println(user);
        userRepository.save(user);
    }

    public void deleteUser(int userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalArgumentException("This user with id = " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(int userId, String username, String password, String usertype) {
        Optional<User> l = userRepository.findById(userId);
        if(l.isEmpty()){
            throw new IllegalArgumentException("This user with id = " + userId + " does not exist");
        }
        User user = l.get();
        if (username != null && !username.isEmpty() && !user.getUsername().equals(username)) {
            Optional<User> user2 = userRepository.findByUserName(username);
            if(user2.isPresent()){
                throw new IllegalArgumentException("This user with username = " + username + " already exists");
            }
            user.setUsername(username);
        }
        if (password != null && !password.isEmpty() && !user.getPassword().equals(password)) {
            user.setPassword(password);
        }
        if (usertype != null && !usertype.isEmpty() && !user.getUserType().equals(usertype)) {
            user.setUserType(usertype);
        }
    }

    public User findUserByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.orElse(null);
    }
}
