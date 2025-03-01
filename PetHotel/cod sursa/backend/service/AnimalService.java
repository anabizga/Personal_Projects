package hotelanimale.is.service;

import hotelanimale.is.model.Animal;
import hotelanimale.is.repository.AnimalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final MedicalInfoService medicalInfoService;

    @Autowired
    public AnimalService(AnimalRepository animalRepository, MedicalInfoService medicalInfoService) {
        this.animalRepository = animalRepository;
        this.medicalInfoService = medicalInfoService;
    }

    public Animal findAnimalById(int id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.orElse(null);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public void addAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    @Transactional
    public void updateAnimal(int animalId, String name, int age, String gender) {
        Optional<Animal> a = animalRepository.findById(animalId);
        if (a.isEmpty()) {
            throw new IllegalArgumentException("This animal does not exist");
        }
        Animal animal = a.get();
        if(name != null && !name.isEmpty() && !animal.getName().equals(name)) {
            animal.setName(name);
        }
        if(age != 0 && age != animal.getAge()) {
            animal.setAge(age);
        }
        if(gender != null && !gender.isEmpty() && !animal.getGender().equals(gender)) {
            animal.setGender(gender);
        }
    }

    public List<Animal> findAnimalByClientId(int clientId){
        Optional<List<Animal>> a = animalRepository.findByClientId(clientId);
        if(a.isPresent()) {
            return a.get();
        } else {
            throw new IllegalArgumentException("There are no animals associated with client id " + clientId);
        }
    }

    public void deleteAnimal(int animalId) {
        animalRepository.deleteById(animalId);
    }

    public void deleteAnimalByClientId(int clientId) {
        List<Animal> animals = findAnimalByClientId(clientId);
        for ( Animal animal : animals ) {
            medicalInfoService.deleteByAnimalId(animal.getId());
        }
        animalRepository.deleteByClientId(clientId);
    }
}
