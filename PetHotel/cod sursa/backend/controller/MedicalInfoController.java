package hotelanimale.is.controller;

import hotelanimale.is.service.MedicalInfoService;
import hotelanimale.is.model.Animal;
import hotelanimale.is.model.MedicalInfo;
import hotelanimale.is.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/medicalInfo")
public class MedicalInfoController {
    private final MedicalInfoService medicalInfoService;
    private final AnimalService animalService;

    @Autowired
    public MedicalInfoController(MedicalInfoService medicalInfoService, AnimalService animalService) {
        this.medicalInfoService = medicalInfoService;
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMedicalInfo() {
        try {
            List<MedicalInfo> medicalInfos = medicalInfoService.getAllMedicalInfo();
            return ResponseEntity.ok(medicalInfos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch medical info: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addMedicalInfo(@RequestBody MedicalInfo medicalInfo) {
        try {
            if (medicalInfo.getAnimal() == null || medicalInfo.getAnimal().getId() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Animal ID is missing or invalid."));
            }

            int animalId = medicalInfo.getAnimal().getId();
            Animal animal = animalService.findAnimalById(animalId);

            if (animal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Animal not found for the given ID: " + animalId));
            }

            medicalInfo.setAnimal(animal);
            MedicalInfo savedMedicalInfo = medicalInfoService.addMedicalInfo(medicalInfo);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicalInfo); // Return the saved medical info
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to add medical info", "details", e.getMessage()));
        }
    }



    @DeleteMapping(path = "{MedicalInfoId}")
    public ResponseEntity<?> deleteMedicalInfo(@PathVariable("MedicalInfoId") int id) {
        try {
            if (medicalInfoService.findMedicalInfoById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical info not found.");
            }
            medicalInfoService.deleteMedicalInfo(id);
            return ResponseEntity.ok("Medical info deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete medical info: " + e.getMessage());
        }
    }

    @PutMapping(path = "{MedicalInfoId}")
    public ResponseEntity<?> updateMedicalInfo(
            @PathVariable("MedicalInfoId") int id,
            @RequestBody MedicalInfo medicalInfoUpdate) {
        try {
            MedicalInfo existingMedicalInfo = medicalInfoService.findMedicalInfoById(id);
            if (existingMedicalInfo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical info not found.");
            }

            existingMedicalInfo.setMedicalDescription(
                    medicalInfoUpdate.getMedicalDescription() != null
                            ? medicalInfoUpdate.getMedicalDescription()
                            : existingMedicalInfo.getMedicalDescription()
            );

            existingMedicalInfo.setMedicalType(
                    medicalInfoUpdate.getMedicalType() != null
                            ? medicalInfoUpdate.getMedicalType()
                            : existingMedicalInfo.getMedicalType()
            );

            medicalInfoService.updateMedicalInfo(existingMedicalInfo.getId(), existingMedicalInfo.getMedicalType(), existingMedicalInfo.getMedicalDescription());
            return ResponseEntity.ok("Medical info updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update medical info: " + e.getMessage());
        }
    }

    @GetMapping(path = "/byAnimalId")
    public ResponseEntity<?> getMedicalInfoByAnimalId(@RequestParam int animalId) {
        try {
            List<MedicalInfo> medicalInfos = medicalInfoService.getMedicalInfoByAnimalId(animalId);
            if (medicalInfos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No medical info found for the specified animal.");
            }
            return ResponseEntity.ok(medicalInfos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch medical info by animal ID: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteByAnimalId")
    public ResponseEntity<?> deleteMedicalInfoByAnimalId(@RequestParam int animalId) {
        try {
            List<MedicalInfo> medicalInfos = medicalInfoService.getMedicalInfoByAnimalId(animalId);
            if (medicalInfos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No medical info found for the specified animal.");
            }
            medicalInfoService.deleteByAnimalId(animalId);
            return ResponseEntity.ok("Medical info for animal deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete medical info by animal ID: " + e.getMessage());
        }
    }
}
