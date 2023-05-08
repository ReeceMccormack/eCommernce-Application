package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepo;
import com.udacity.jdnd.course3.critter.repo.PetRepo;
import com.udacity.jdnd.course3.critter.repo.ScheduleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final EmployeeRepo employeeRepo;
    private final PetRepo petRepo;

    public ScheduleService(ScheduleRepo scheduleRepo,
                           EmployeeRepo employeeRepo,
                           PetRepo petRepo) {
        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
    }

    public List<Schedule> getScheduleForPet(Long id){
        return  scheduleRepo.findPetsById(id);
    }

    public List<Schedule> getScheduleForEmployee(Long id){
        return scheduleRepo.findEmployeesById(id);
    }

    public List<Schedule> getScheduleForCustomer(Long id){
        return scheduleRepo.findPetsById(id);
    }

    public List<Schedule> getSchedules(){
        return scheduleRepo.findAll();
    }

    public Schedule createSchedule (Schedule schedule, List<Long> employeeIds, List<Long> petIds)

    {
        List<Employee> employees = employeeRepo.findAllById(employeeIds);
        List<Pet> pets = petRepo.findAllById(petIds);
        schedule.setEmployeeList(employees);
        schedule.setPetList(pets); if (schedule == null)
    { schedule = new Schedule (); }
        return scheduleRepo.save(schedule);

    }
}
