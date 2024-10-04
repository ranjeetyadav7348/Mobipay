package com.mobipay.demo.ServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.time.LocalDate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobipay.demo.AccountDto.EMIDetailsDTO;
import com.mobipay.demo.AccountDto.LoanAccountDTO;
import com.mobipay.demo.Entity.EmiDetails;
import com.mobipay.demo.Entity.LoanAccount;
import com.mobipay.demo.Repository.LoanAccountRepository;
import com.mobipay.demo.Service.LoanAccountService;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LoanAccountServiceImp implements LoanAccountService {

    private static final Logger logger = LoggerFactory.getLogger(LoanAccountServiceImp.class);

    @Autowired
    private LoanAccountRepository loanAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    protected static SSLContext sc;
    static {

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                // No need to implement.
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                // No need to implement.
            }
        } };

        // Install the all-trusting trust manager
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    public LoanAccount getLoanAccountDetails(String loanAccountNumber) {
        logger.info("Received request to fetch loan account details for account number: {}", loanAccountNumber);

       
      

        try {

            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setSslcontext(sc).build();
            CloseableHttpResponse execute = closeableHttpClient
                    .execute(new HttpGet("https://demo9993930.mockable.io/loanaccount/"+loanAccountNumber));

            String response = EntityUtils.toString(execute.getEntity());
            

            if (response == null) {
                logger.warn("No response received for account number: {}", loanAccountNumber);
                return null; // You can handle this as needed
            }

            logger.debug("Response from external API: {}", response);


            ObjectMapper objectMapper = new ObjectMapper();
            LoanAccountDTO loanAccountDTO = objectMapper.readValue(response, LoanAccountDTO.class);


            LoanAccount loanAccount = modelMapper.map(loanAccountDTO, LoanAccount.class);

            // Handle EMIDetails relationship
            for (EMIDetailsDTO emiDTO : loanAccountDTO.getEmiDetails()) {
                EmiDetails emiDetails = modelMapper.map(emiDTO, EmiDetails.class);
                emiDetails.setLoanAccount(loanAccount); // Set the relationship
                loanAccount.getEmiDetails().add(emiDetails);
                
            }

         
            

            // Persist into DB
            // loanAccountRepository.save(loanAccount);
            logger.info("Successfully saved loan account details for account number: {}", loanAccountNumber);

            return  loanAccountRepository.save(loanAccount);

        } catch (Exception ex) {
            logger.error("Error occurred while fetching loan account details for account number: {}", loanAccountNumber,
                    ex);
            return null; // Handle this according to your application's needs
        }
    }

}
