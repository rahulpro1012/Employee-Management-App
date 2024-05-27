package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDto;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}/get")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/skills")
    public ResponseEntity<EmployeeDto> updateSkills(@PathVariable Long id, @RequestBody String skills) {
        EmployeeDto employeeDto = employeeService.newSkills(id, skills);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<EmployeeDto> updateRating(@PathVariable Long id, @RequestParam Integer rating) {
        EmployeeDto employeeDto = employeeService.addRating(id, rating);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }


     @GetMapping("/search")
     public ResponseEntity<List<EmployeeDto>> searchEmployeeBySkill(@RequestParam String skill) {
        List<EmployeeDto> listOfEmployeeBySkill=employeeService.getEmployeesBySkill(skill);
        return new ResponseEntity<>(listOfEmployeeBySkill,HttpStatus.OK);
     }
}
