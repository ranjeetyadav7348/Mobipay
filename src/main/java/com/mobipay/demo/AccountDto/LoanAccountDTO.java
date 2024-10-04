package com.mobipay.demo.AccountDto;

import java.util.List;

public class LoanAccountDTO {
    private String loanAccountNumber;
    private List<EMIDetailsDTO> emiDetails;

    // Getters and Setters
    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public List<EMIDetailsDTO> getEmiDetails() {
        return emiDetails;
    }

    public void setEmiDetails(List<EMIDetailsDTO> emiDetails) {
        this.emiDetails = emiDetails;
    }
}
