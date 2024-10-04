package com.mobipay.demo.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name="emi")
public class EmiDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String month;
    private double emiAmount;
    private boolean paidStatus;
    private boolean dueStatus;

    @ManyToOne
    @JoinColumn(name = "loan_account_number")
    private LoanAccount loanAccount;
   

}
