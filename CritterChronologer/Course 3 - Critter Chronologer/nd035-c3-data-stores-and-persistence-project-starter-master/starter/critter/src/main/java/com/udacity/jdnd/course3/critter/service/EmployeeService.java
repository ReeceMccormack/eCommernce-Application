package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeRequest;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepo;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee saveEmployee (Employee employee){
        return this.employeeRepo.save(employee);
    }

    public Employee getEmployee (Long id){
        return employeeRepo.getOne(id);
    }

    public void setAvailability(Set<DayOfWeek> onShift, Long id){
        Employee employee = getEmployee(id);
        employee.setDaysAvailable(onShift);
        employeeRepo.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequest employeeRequest){
        Set<EmployeeSkill> skills = employeeRequest.getSkills();
        DayOfWeek date = employeeRequest.getDate().getDayOfWeek();
        List<Employee> employeeList = employeeRepo.findAll();
        List<Employee> employeeAvailable = new ArrayList<>();
        for (Employee employee : employeeList){
          if (employee.getDaysAvailable().contains(date)){
    employeeAvailable.add(employee);
            }
        }
        return employeeAvailable;
    }
}
