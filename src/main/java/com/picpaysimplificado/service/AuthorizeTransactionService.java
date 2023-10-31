package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthorizeTransactionService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.authorizationApi}")
    private String authApiUrl;

    public boolean authorizeTransaction(User payer, BigDecimal value) {

        ResponseEntity<Map> authorizationTransaction = restTemplate.getForEntity(this.authApiUrl, Map.class);

        if (authorizationTransaction.getStatusCode() == HttpStatus.OK){
            String message = (String) Objects.requireNonNull(authorizationTransaction.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        else {
            return false;
        }
    }
}
