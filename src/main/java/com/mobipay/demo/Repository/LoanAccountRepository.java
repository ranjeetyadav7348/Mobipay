package com.mobipay.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobipay.demo.Entity.LoanAccount;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, String> {
}
