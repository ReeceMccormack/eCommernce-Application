package com.udacity.jdnd.course3.critter.Controllers;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    private static Pet converPetToEntity(PetDTO petDTO){
        Pet pet = new Pet();
            BeanUtils.copyProperties(petDTO, pet);
                    Customer customer = new Customer();
                 customer.setId(petDTO.getOwnerId());
            pet.setCustomer(customer);
        return pet;
    }

    private static PetDTO converPetToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
         BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
                return petDTO;
    }


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
       Pet pet = petService.savePet(converPetToEntity(petDTO));
       return converPetToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return converPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> petList = petService.getPets();
        petList.forEach(pet -> petDTOS.add(converPetToDTO(pet)));
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> petList = petService.getPetsByOwner(ownerId);
        petList.forEach(pet -> petDTOS.add(converPetToDTO(pet)));
        return petDTOS;
    }
}
