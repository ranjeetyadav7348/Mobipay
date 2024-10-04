package com.mobipay.demo.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Loan")
public class LoanAccount {

    @Id
    private String loanAccountNumber; // Ensure this matches the database type

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanAccount")
    private List<EmiDetails> emiDetails;

   
}