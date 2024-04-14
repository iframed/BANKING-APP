package com.example.emailsApp.dto;

import java.util.Date;
import java.util.List;

import com.example.emailsApp.entity.CompteStatus;

import lombok.Data;

@Data
public class OperationPageDtos {
    private String id;
    private double solde;
    private int pageactuelle;
    private int totalepages;
    private int pagesize;

    private Date datecreation;
    private CompteStatus status;

    private List<OperationDto> operationDto;
}
