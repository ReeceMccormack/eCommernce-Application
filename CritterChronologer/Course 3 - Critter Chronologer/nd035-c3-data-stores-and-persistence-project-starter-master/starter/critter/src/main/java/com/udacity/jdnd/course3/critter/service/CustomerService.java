package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repo.CustomerRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getCustomer (Long id){
        return customerRepo.getOne(id);
    }

    public Customer saveCustomer(Customer customer){
        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return  customerRepo.findAll();
    }

    public List<Customer> getOwnerByPet(Long id){
        return customerRepo.findPetsById(id);
    }

    public void addPetToCustomer(Pet pet, Customer customer){
        List<Pet> petList = customer.getPetList();
        if (petList == null){
            petList = new ArrayList<>();
        }
        petList.add(pet);
        customer.setPetList(petList);
        customerRepo.save(customer);
    }
}
