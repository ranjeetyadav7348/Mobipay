package com.mobipay.demo.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoanAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String loanAccountNumber;
    private LocalDate dueDate;
    private double emiAmount;

   
}