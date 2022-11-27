package edu.ivgallo.contactservice.controller;

import edu.ivgallo.contactservice.entity.Contact;
import edu.ivgallo.contactservice.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody @Valid  Contact newContact){

        Contact savedContact = contactRepository.save(newContact);
        return ResponseEntity.
                created(URI.create("/contacts/" + savedContact.getId())).
                body(savedContact);

    }

    @GetMapping
    public Iterable<Contact> getAllContact(){
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getContact(@PathVariable("id") Integer id) {
        return contactRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "The contact " + id + " does not exist"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") Integer id, @RequestBody @Valid Contact contact){

        if (!contactRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The contact " + id + " does not exist");
        }

        contact.setId(id);
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(savedContact);

    }


    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable("id") Integer id){

        if (!contactRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The contact " + id + " does not exist");
        }

        contactRepository.deleteById(id);
    }

}
