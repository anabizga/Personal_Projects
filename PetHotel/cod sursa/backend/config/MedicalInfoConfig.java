package hotelanimale.is.config;

import hotelanimale.is.repository.MedicalInfoRepository;
import hotelanimale.is.model.Animal;
import hotelanimale.is.model.MedicalInfo;
import hotelanimale.is.repository.AnimalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MedicalInfoConfig {
    @Bean
    CommandLineRunner medicalInfoCommandLineRunner(MedicalInfoRepository medicalInfoRepository, AnimalRepository animalRepository) {
        return args -> {
            Animal animal1 = animalRepository.findById(1).orElseThrow();
            Animal animal2 = animalRepository.findById(2).orElseThrow();

            MedicalInfo medicalInfo1 = new MedicalInfo("Routine checkup", "General", animal1);
            MedicalInfo medicalInfo2 = new MedicalInfo("Vaccination", "Preventive", animal2);

            medicalInfoRepository.saveAll(List.of(medicalInfo1, medicalInfo2));
        };
    }
}
