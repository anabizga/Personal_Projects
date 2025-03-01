package hotelanimale.is.service;
import hotelanimale.is.model.Contact;
import hotelanimale.is.model.EmailValidator;
import hotelanimale.is.model.PhoneNumberValidator;
import hotelanimale.is.repository.ContactRepository;
import hotelanimale.is.repository.UserRepository;
import hotelanimale.is.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact addContact(Contact contact) {
        User user = userRepository.findById(contact.getUser().getId()).orElse(null);
        if(user != null) {
            if(contact.getType().equals("Email")){
                EmailValidator.validate(contact.getInfo());
            }
            if (contact.getType().equals("Phone")) {
                PhoneNumberValidator.validate(contact.getInfo());
            }
            contact.setUser(user);
            contactRepository.save(contact);
        } else {
            throw new IllegalArgumentException("This user does not exist");
        }
        return contact;
    }

    public void deleteContact(int contactId) {
       boolean exists = contactRepository.existsById(contactId);
       if(exists) {
           contactRepository.deleteById(contactId);
       } else {
           throw new IllegalArgumentException("This contact does not exist");
       }
    }

    @Transactional
    public void updateContact(int id, String type, String info) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if(contact == null) {
            throw new IllegalArgumentException("This contact does not exist");
        }
        if(type!=null && !type.isEmpty() && !type.equals(contact.getType())) {
            contact.setType(type);
        }
        if(info!=null && !info.isEmpty() && !info.equals(contact.getInfo())) {
            if(type.equals("Email")){
                EmailValidator.validate(info);
            }
            if (type.equals("Phone")) {
                PhoneNumberValidator.validate(info);
            }
            contact.setInfo(info);
        }
    }

    public List<Contact> getContactsByUserId(int userId) {
        Optional<List<Contact>> contacts = contactRepository.findByUserId(userId);
        if(contacts.isPresent()) {
            return contacts.get();
        } else {
            throw new IllegalArgumentException("There is no contact with user id " + userId);
        }
    }

    public void deleteContactsByUserId(int userId) {
        contactRepository.deleteByUserId(userId);
    }
}
