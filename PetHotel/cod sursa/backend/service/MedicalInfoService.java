package hotelanimale.is.service;

import hotelanimale.is.model.MedicalInfo;
import hotelanimale.is.repository.MedicalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalInfoService {
    private final MedicalInfoRepository medicalInfoRepository;

    @Autowired
    public MedicalInfoService(MedicalInfoRepository medicalInfoRepository) {
        this.medicalInfoRepository = medicalInfoRepository;
    }

    public MedicalInfo findMedicalInfoById(int id) {
        Optional<MedicalInfo> medicalInfo = medicalInfoRepository.findById(id);
        return medicalInfo.orElse(null);
    }

    public List<MedicalInfo> getAllMedicalInfo() {
        return medicalInfoRepository.findAll();
    }

    public MedicalInfo addMedicalInfo(MedicalInfo medicalInfo) {
        medicalInfoRepository.save(medicalInfo);
        return medicalInfo;
    }

    public void deleteMedicalInfo(int id) {
        boolean exists = medicalInfoRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("MedicalInfo with id " + id + " does not exist.");
        }
        medicalInfoRepository.deleteById(id);
    }

    public void updateMedicalInfo(int id, String medicalDescription, String medicalType) {
        Optional<MedicalInfo> optionalMedicalInfo = medicalInfoRepository.findById(id);
        if (optionalMedicalInfo.isEmpty()) {
            throw new IllegalArgumentException("MedicalInfo with id " + id + " does not exist.");
        }
        MedicalInfo medicalInfo = optionalMedicalInfo.get();

        if (medicalDescription != null && !medicalDescription.isEmpty()) {
            medicalInfo.setMedicalDescription(medicalDescription);
        }
        if (medicalType != null && !medicalType.isEmpty()) {
            medicalInfo.setMedicalType(medicalType);
        }
        medicalInfoRepository.save(medicalInfo);
    }

    public List<MedicalInfo> getMedicalInfoByAnimalId(int animalId) {
        Optional<List<MedicalInfo>> medicalInfos = medicalInfoRepository.findByAnimalId(animalId);
        if (medicalInfos.isEmpty()) {
            throw new IllegalArgumentException("MedicalInfo with animalId " + animalId + " does not exist.");
        }
        return medicalInfos.get();
    }

    public void deleteByAnimalId(int animalId) {
        medicalInfoRepository.deleteByAnimalId(animalId);
    }
}
