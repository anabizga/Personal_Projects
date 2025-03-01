package hotelanimale.is.service;

import hotelanimale.is.model.Admin;
import hotelanimale.is.repository.AdminRepository;
import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public List<Admin> getAdminsByLocationId(int locationId) {
        Optional<List<Admin>> admins = adminRepository.findByLocationId(locationId);
        return admins.orElse(null);
    }

    public void addAmin(Admin admin) {
        User user = userRepository.findById(admin.getUser().getId()).orElse(null);
        if(!user.getUserType().equals("Admin")){
            throw new IllegalArgumentException("This user is not an Admin");
        }
        admin.setUser(user);
        adminRepository.save(admin);
    }

    public void deleteAdmin(int adminId) {
        boolean exits = adminRepository.existsById(adminId);
        if(exits){
            adminRepository.deleteById(adminId);
        } else {
            throw new IllegalArgumentException("This admin does not exist");
        }
    }

    @Transactional
    public void updateAdmin(int adminId, String firstName, String lastName) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        if(admin == null){
            throw new IllegalArgumentException("This admin does not exist");
        }
        if(firstName!=null && !firstName.isEmpty() && !firstName.equals(admin.getFirstName())){
            admin.setFirstName(firstName);
        }
        if(lastName!=null && !lastName.isEmpty() && !lastName.equals(admin.getLastName())){
            admin.setLastName(lastName);
        }
    }

    public Admin getAdminByUsername(String username) {
        Optional <Admin> admin = adminRepository.findByUsername(username);
        if(admin.isPresent()){
            return admin.get();
        } else {
            throw new IllegalArgumentException("This user does not exist");
        }
    }

    public Admin getAdminByUserId(int id){
        Optional<Admin> admin = adminRepository.findByUserId(id);
        if(admin.isPresent()){
            return admin.get();
        } else {
            throw new IllegalArgumentException("This user does not exist");
        }
    }

    public void deleteAdminByUserId(int id) {
        adminRepository.deleteByUserId(id);
    }
}

