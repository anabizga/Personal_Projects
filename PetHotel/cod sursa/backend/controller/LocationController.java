package hotelanimale.is.controller;

import hotelanimale.is.service.LocationService;
import hotelanimale.is.model.Location;
import hotelanimale.is.service.ReservationService;
import hotelanimale.is.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(path = "api/locations")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    private final LocationService locationService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    @Autowired
    public LocationController(LocationService locationService, RoomService roomService, ReservationService reservationService) {
        this.locationService = locationService;
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable int locationId) {
        return locationService.getLocationById(locationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Location> registerNewLocation(@RequestBody Location location) {
        Location newLocation = locationService.addNewLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLocation);
    }

    @PutMapping(path = "/{locationId}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable("locationId") int locationId,
            @RequestBody Location updatedLocation) {
        Location updated = locationService.updateLocation(locationId, updatedLocation.getName(), updatedLocation.getAddress(), updatedLocation.getPhoneNumber());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("locationId") int locationId) {
        reservationService.deleteReservationByLocationId(locationId);
        roomService.deleteByLocationId(locationId);
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
