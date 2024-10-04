package com.mobipay.demo.Service;

import com.mobipay.demo.Entity.LoanAccount;

public interface LoanAccountService {

    LoanAccount getLoanAccountDetails(String loanAccountNumber);
    
    
}
