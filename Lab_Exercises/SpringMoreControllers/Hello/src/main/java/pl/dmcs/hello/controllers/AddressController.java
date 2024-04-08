package pl.dmcs.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.hello.model.Address;
import pl.dmcs.hello.repository.StudentRepository;
import pl.dmcs.hello.repository.AddressRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressRepository studentRepository;
    private AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository studentRepository, AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
    }
    @GetMapping
    public List<Address> findAllAddresss() {return addressRepository.findAll();}

    @GetMapping(value = "/{id}")
    public Address findAddress(@PathVariable("id") long id) {return addressRepository.findById(id);}
    @PutMapping
    public ResponseEntity<Address> updateAddressList(@RequestBody List<Address> updates){
        addressRepository.deleteAll();
        addressRepository.saveAll(updates);
        return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @PathVariable("id") long id){
        address.setId(id);
        addressRepository.save(address);
        return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address){
        addressRepository.save(address);
        return new ResponseEntity<Address>(address, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Address> updatePartOfAddress(@RequestBody Map<String, Object> updates, @PathVariable("id") long id){
        Address address = addressRepository.findById(id);
        if(address == null){
            System.out.println("Address not found");
            return new ResponseEntity<Address>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(address, updates);
        return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Address address, Map<String, Object> updates){
        if (updates.containsKey("street")) {
            address.setStreet((String) updates.get("street"));
        }
        if (updates.containsKey("city")) {
            address.setCity((String) updates.get("city"));
        }
        addressRepository.save(address);
    }

    @DeleteMapping
    public ResponseEntity<Address> deleteAll(){
        addressRepository.deleteAll();
        return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("id") long id){
        addressRepository.delete(addressRepository.findById(id));
        return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
    }
}
