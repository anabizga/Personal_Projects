package hotelanimale.is.controller;

import java.util.List;

import hotelanimale.is.service.ContactService;
import hotelanimale.is.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @DeleteMapping(path = "{contactId}")
    public void deleteContact(@PathVariable("contactId") int contactId) {
        contactService.deleteContact(contactId);
    }

    @PutMapping(path = "{contactId}")
    public void updateContact(@PathVariable("contactId") int contactId, @RequestParam(required = false) String type, @RequestParam(required = false) String info) {
        contactService.updateContact(contactId, type, info);
    }

    @GetMapping(path = "/byUserId")
    public List<Contact> getContactsByUserId(@RequestParam int userId) {
        List<Contact> contacts = contactService.getContactsByUserId(userId);
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        return contacts;
    }

    @DeleteMapping(path = "/deleteUserId")
    public void deleteContactsByUserId(@RequestParam int userId) {
        contactService.deleteContactsByUserId(userId);
    }
}
