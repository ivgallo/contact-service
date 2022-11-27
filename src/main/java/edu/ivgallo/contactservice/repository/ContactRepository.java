package edu.ivgallo.contactservice.repository;

import edu.ivgallo.contactservice.entity.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

}
