package com.udacity.jdnd.course3.critter.Controllers;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeRequest;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    private  final PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService, PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    private static CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPetList() != null){
            List<Long> petId = new ArrayList<>();
            customer.getPetList().forEach(pet -> petId.add(pet.getId()));
            customerDTO.setPetIds(petId);
        }
        return customerDTO;
    }

    private static Customer convertCustomerToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if (customerDTO.getPetIds() != null) {
            List<Pet> petList = new ArrayList<>();
            customerDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                petList.add(pet);}); customer.setPetList(petList);
        }
        return customer;
    }

    private static EmployeeDTO convertEmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private static Employee convertEmployeeToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private static EmployeeRequest convertEmployeeRequest(EmployeeRequestDTO employeeRequestDTO){
        EmployeeRequest employeeRequest = new EmployeeRequest();
        BeanUtils.copyProperties(employeeRequestDTO, employeeRequest);
        return employeeRequest;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.saveCustomer(convertCustomerToEntity(customerDTO));
        return convertCustomerToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customerList = customerService.getAllCustomers();
        customerList.forEach(customer -> customerDTOS.add(convertCustomerToDTO(customer)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);
        CustomerDTO customerDTO = convertCustomerToDTO(customerService.getCustomer(pet.getCustomer().getId()));
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.saveEmployee(convertEmployeeToEntity(employeeDTO));
        return convertEmployeeToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> onShift, @PathVariable long employeeId) {
       employeeService.setAvailability(onShift, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        EmployeeRequest employeeRequest = convertEmployeeRequest(employeeDTO);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        List<Employee> employeeList = employeeService.findEmployeesForService(employeeRequest);
        employeeList.forEach(employee -> employeeDTOS.add(convertEmployeeToDTO(employee)));
        return employeeDTOS;
    }

}
