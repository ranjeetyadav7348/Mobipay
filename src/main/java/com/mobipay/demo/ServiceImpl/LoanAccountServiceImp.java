package com.mobipay.demo.ServiceImpl;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mobipay.demo.Entity.LoanAccount;
import com.mobipay.demo.Repository.LoanAccountRepository;

@Service
public class LoanAccountServiceImp {

    private static final Logger logger = LoggerFactory.getLogger(LoanAccountServiceImp.class);

    @Autowired
    private LoanAccountRepository loanAccountRepository;

    public LoanAccount getLoanAccountDetails(String loanAccountNumber) {
        logger.info("Received request to fetch loan account details for account number: {}", loanAccountNumber);

        String url = "https://demo9993930.mockable.io/loanaccount/" + loanAccountNumber;
        logger.debug("Calling external API at URL: {}", url);
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                logger.warn("No response received for account number: {}", loanAccountNumber);
                return null; // You can handle this as needed
            }

            logger.debug("Response from external API: {}", response);

            LoanAccount loanAccount = new LoanAccount();
            loanAccount.setLoanAccountNumber(loanAccountNumber);
            loanAccount.setDueDate(LocalDate.parse((String) response.get("dueDate")));
            loanAccount.setEmiAmount((Double) response.get("emiAmount"));

            // Persist into DB
            loanAccount = loanAccountRepository.save(loanAccount);
            logger.info("Successfully saved loan account details for account number: {}", loanAccountNumber);

            return loanAccount;

        } catch (Exception ex) {
            logger.error("Error occurred while fetching loan account details for account number: {}", loanAccountNumber, ex);
            return null; // Handle this according to your application's needs
        }
    }
}
