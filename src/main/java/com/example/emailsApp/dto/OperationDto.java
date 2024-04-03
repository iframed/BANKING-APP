package com.example.emailsApp.dto;



import java.util.Date;

import com.example.emailsApp.entity.OpetationType;

import lombok.Data;

@Data
public class OperationDto {
    
    
private long id;

private Date date;

 private double montant;


 private OpetationType type;


 private String description;

 
}
