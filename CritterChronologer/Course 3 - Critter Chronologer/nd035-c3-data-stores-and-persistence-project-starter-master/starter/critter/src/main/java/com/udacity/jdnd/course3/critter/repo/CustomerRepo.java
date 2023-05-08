package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findPetsById(Long id);



}
