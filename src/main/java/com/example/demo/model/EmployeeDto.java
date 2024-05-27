package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String role;
    private String[] skills;
    private Integer rating;
}
