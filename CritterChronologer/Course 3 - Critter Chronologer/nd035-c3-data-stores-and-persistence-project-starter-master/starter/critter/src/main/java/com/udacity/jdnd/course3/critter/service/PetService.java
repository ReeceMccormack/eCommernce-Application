package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.repo.PetRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepo petRepo;
    private final CustomerService customerService;

    public PetService(PetRepo petRepo, CustomerService customerService) {
        this.petRepo = petRepo;
        this.customerService = customerService;
    }

    public Pet savePet (Pet pet){
        Pet storedPet = petRepo.save(pet);
        customerService.addPetToCustomer(storedPet, storedPet.getCustomer());
        return storedPet;
    }

    public Pet getPet(Long id){
        return petRepo.getOne(id);
    }

    public List<Pet> getPets(){
        return petRepo.findAll();
    }

    public List<Pet> getPetsByOwner (Long id){
        return petRepo.findPetsByCustomerId(id);
    }



}
