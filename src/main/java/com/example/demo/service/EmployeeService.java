package com.example.demo.service;

import com.example.demo.model.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
     EmployeeDto newSkills(Long id,String skills);

     EmployeeDto addRating(Long id,Integer rating);

     List<EmployeeDto> getEmployeesBySkill(String skill);
}
