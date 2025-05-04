package com.github.mbonisimpala.movieshop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.repository.AccountRepository;
import com.github.mbonisimpala.movieshop.security.SecurityConstants;
import com.github.mbonisimpala.movieshop.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    private String token;
    private Account testAccount;

    @BeforeEach
    void setup(TestInfo testInfo){
        // skip the test class that add the account as part of testing
        if (!testInfo.getDisplayName().contains("shouldReturnAccount_WhenSavingValidAccount")){
            testAccount = new Account("testuser@gmail.com", "0000");
            accountService.addAccount(testAccount); // add a test account to record

            // provide JWT for the test account
            token = JWT.create()
                    .withSubject("testuser@gmail.com")
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        }

    }

    @AfterEach
    void cleanup(){
        accountRepository.deleteAll();
    }

    @Test
    public void shouldReturnAccountId_whenGettingExistingEmail() throws Exception{
        // Given an existing email
        String email = testAccount.getEmail();

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/account/" + email)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(testAccount.getId())));
    }

    @Test
    public void shouldReturnNotFound_whenGettingNonExistingEmail() throws Exception{
        // Given non-existing email
        String email = "nonexisting@mail.com";

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/account/" + email)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The account with email or id '" + email + "' does not exist in our records."));
    }

    @Test
    public void shouldReturnAccount_WhenSavingValidAccount() throws Exception{
        // Given valid account
        testAccount = new Account("mpalambonisi63@gmail.com", "0000");
        testAccount.setId(1L);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/account/register")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAccount));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequest_whenSavingInvalidAccount() throws Exception{
        // Given an invalid account
        testAccount = new Account("   ", "");

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/account/register")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAccount));

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.length()").value(2))
                .andExpect(jsonPath("$.message", hasItems(
                        "email cannot be blank",
                        "password cannot be blank"
                )));
    }

    @Test
    public void shouldReturnNoContent_whenDeletingExistingAccount() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/account/" + testAccount.getId())
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFound_whenDeletingNonExistingAccount() throws Exception{
        // Given non-existing account ID
        long accountId = 999L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/account/" + accountId)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("Cannot delete non-existing resource"));
    }

}
