package com.mobipay.demo.Controller;

import com.mobipay.demo.Entity.LoanAccount;
import com.mobipay.demo.ServiceImpl.LoanAccountServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/assignment/")
public class LoanAccountController {

    private static final Logger logger = LoggerFactory.getLogger(LoanAccountController.class);

    @Autowired
    private LoanAccountServiceImp loanAccountService;

    @GetMapping("/loanaccount/{loanAccountNumber}")
    public ResponseEntity<?> getLoanAccount(@PathVariable String loanAccountNumber) {
        logger.info("Received request to fetch loan account details for account number: {}", loanAccountNumber);

        try {
            LoanAccount loanAccount = loanAccountService.getLoanAccountDetails(loanAccountNumber);

            if (loanAccount == null) {
                logger.warn("Loan account not found for account number: {}", loanAccountNumber);
                return new ResponseEntity<>("Loan account not found", HttpStatus.NOT_FOUND);
            }

            logger.info("Successfully fetched loan account details for account number: {}", loanAccountNumber);
            return new ResponseEntity<>(loanAccount, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Error occurred while fetching loan account details for account number: {}", loanAccountNumber,
                    ex);
            return new ResponseEntity<>("An error occurred while processing your request.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
