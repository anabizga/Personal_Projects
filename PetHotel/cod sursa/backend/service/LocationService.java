package hotelanimale.is.service;

import hotelanimale.is.model.Location;
import hotelanimale.is.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getLocation(int id) {
        return locationRepository.findById(id).orElse(null);
    }

    public List<Location> getLocations(){
        return locationRepository.findAll();
    }

    public Location addNewLocation(Location location) {
        Optional<Location> l = locationRepository.findByName(location.getName());
        if (l.isPresent()) {
            throw new IllegalArgumentException("This location already exists");
        }
        System.out.println(location);
        locationRepository.save(location);
        return location;
    }

    public Optional<Location> getLocationById(int id) {
        return locationRepository.findById(id);
    }

    public void deleteLocation(int locationId) {
        boolean exists = locationRepository.existsById(locationId);
        if(!exists){
            throw new IllegalArgumentException("This location with id = " + locationId + " does not exist");
        }
        locationRepository.deleteById(locationId);
    }

    @Transactional
    public Location updateLocation(int locationId, String name, String address, String phone) {
        Optional<Location> l = locationRepository.findById(locationId);
        if(l.isEmpty()){
            throw new IllegalArgumentException("This location with id = " + locationId + " does not exist");
        }
        Location location = l.get();
        if (name != null && !name.isEmpty() && !location.getName().equals(name)) {
            location.setName(name);
        }
        if (address != null && !address.isEmpty() && !location.getAddress().equals(address)) {
            location.setAddress(address);
        }
        if (phone != null && !phone.isEmpty() && !location.getPhoneNumber().equals(phone)) {
            location.setPhoneNumber(phone);
        }
        return location;
    }

    public Location findLocationByName(String locationName) {
        Optional<Location> location = locationRepository.findByName(locationName);
        if(location.isEmpty()){
            throw new IllegalArgumentException("This location with name = " + locationName + " does not exist");
        }
        return location.get();
    }

}
