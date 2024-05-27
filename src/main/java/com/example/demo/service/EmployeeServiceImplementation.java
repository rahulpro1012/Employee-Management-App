package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDto;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeServiceImplementation implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImplementation.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto newSkills(Long id,String skills) {
        System.out.println("Received skills: " + skills);

        String[] listOfSkills = skills.split(",");
        System.out.println(Arrays.asList(listOfSkills));

        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new RuntimeException());
        employee.setSkills(listOfSkills);

        Employee updatedEmployee=employeeRepository.save(employee);
        return mapEmployeeDTO(updatedEmployee);
    }

    @Override
    public EmployeeDto addRating(Long id, Integer rating) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        employee.setRating(rating);
        return mapEmployeeDTO(employee);
    }


    @Override
    public List<EmployeeDto> getEmployeesBySkill(String skill) {
        List<Employee> listOfEmployee = employeeRepository.findAll();

        // Print debug information
        logger.debug("Specified Skill: {}", skill);
        System.out.println("Specified Skill: " + skill);

        // Filter employees based on the presence of the specified skill in the skills array (case-insensitive)
        List<Employee> listOfEmployeeBySkill = listOfEmployee.stream()
                .filter(employee -> {
                    System.out.println("Employee Skills: " + Arrays.toString(employee.getSkills()));
                    return Arrays.asList(employee.getSkills()).stream()
                            .anyMatch(s -> s.equalsIgnoreCase(skill));
                })
                .collect(Collectors.toList());

        // Map the filtered employees to a list of EmployeeDto
        List<EmployeeDto> employeeDtoList = listOfEmployeeBySkill.stream()
                .map(this::mapEmployeeDTO)
                .collect(Collectors.toList());

        return employeeDtoList;
    }


    public EmployeeDto mapEmployeeDTO(Employee updateEmployee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(updateEmployee.getId());
        employeeDto.setName(updateEmployee.getName());
        employeeDto.setSkills(updateEmployee.getSkills());
        employeeDto.setRating(updateEmployee.getRating());
        employeeDto.setRole(updateEmployee.getRole());
        return employeeDto;
    }
}
