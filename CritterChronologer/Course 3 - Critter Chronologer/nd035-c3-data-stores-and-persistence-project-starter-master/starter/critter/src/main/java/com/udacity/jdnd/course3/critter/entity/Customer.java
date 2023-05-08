package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Pet> petList;
    private String name;

    private String phoneNumber;

    @Column(name="Notes", length = 500)
    private String notes;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pet> getPetList(){
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
