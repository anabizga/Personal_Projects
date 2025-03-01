package hotelanimale.is.service;

import hotelanimale.is.model.Client;
import hotelanimale.is.repository.ClientRepository;
import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void addClient(Client client) {
        User user = userRepository.findById(client.getUser().getId()).orElse(null);
        if(!user.getUserType().equals("Client")){
            throw new IllegalArgumentException("This user is not a Client");
        }
        client.setUser(user);
        clientRepository.save(client);
    }

    public void deleteClient(int clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (exists) {
            clientRepository.deleteById(clientId);
        } else {
            throw new IllegalArgumentException("Client not found");
        }
    }

    @Transactional
    public void updateClient(int clientId, String firstName, String lastName, LocalDate birthday) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        if(firstName!=null && !firstName.isEmpty() && !firstName.equals(client.getFirstName())){
            client.setFirstName(firstName);
        }
        if(lastName!=null && !lastName.isEmpty() && !lastName.equals(client.getLastName())){
            client.setLastName(lastName);
        }
        if(birthday!=null && !birthday.equals(client.getDateOfBirth())){
            client.setDateOfBirth(birthday);
        }
    }

    public Client getClientByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if(client.isPresent()){
            return client.get();
        } else {
            throw new IllegalArgumentException("Client not found");
        }
    }

    public Client getClientByUserId(int id) {
        Optional<Client> client = clientRepository.findByUserId(id);
        if(client.isPresent()){
            return client.get();
        } else {
            throw new IllegalArgumentException("Client not found");
        }
    }

    public void deleteClientByUserId(int userId) {
        clientRepository.deleteByUserId(userId);
    }

    public Client getClientById(int id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

}
