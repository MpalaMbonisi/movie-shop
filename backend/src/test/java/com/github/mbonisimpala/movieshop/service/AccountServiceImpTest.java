package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.exception.AccountNotFoundException;
import com.github.mbonisimpala.movieshop.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImpTest {

    @Mock
    BCryptPasswordEncoder byCryptPasswordEncoder;
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountServiceImp accountService;

    @Test
    public void addAccount_ValidAccount_ShouldReturnAccount(){
        // Arrange
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");

        when(byCryptPasswordEncoder.encode("0000")).thenReturn("encrypted-password");
        when(accountRepository.save(mockAccount)).thenReturn(mockAccount);

        // Act
        Account result = accountService.addAccount(mockAccount);

        // Assert
        Assertions.assertEquals("mpalambonisi63@gmail.com", result.getEmail(), "Account email should match.");
        verify(byCryptPasswordEncoder).encode("0000");
        Assertions.assertEquals("encrypted-password", result.getPassword(), "Account password should be encrypted.");
    }

    @Test
    public void getAccount_ExistingId_ShouldReturnAccount(){
        // Arrange
        long accountId = 1L;
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Act
        Account result = accountService.getAccount(accountId);

        // Assert
        Assertions.assertAll("Account properties",
                () -> Assertions.assertEquals("mpalambonisi63@gmail.com", result.getEmail(), "Account email should match."),
                () -> Assertions.assertEquals("0000", result.getPassword(), "Account password should match.")
        );
    }

    @Test
    public void getAccount_NonExistingId_ShouldThrowException(){
        // Arrange
        long accountId = 999L; // Non-existing ID

        when(accountRepository.findById(accountId)).thenThrow(new AccountNotFoundException(Long.toString(accountId)));

        // Act & Assert
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccount(accountId);
        }, "Should throw AccountNotFoundException.");
    }

    @Test
    public void getAccountIdByEmail_ExistingId_ShouldReturnId(){
        // Arrange
        String mockEmail = "mpalambonisi63@gmail.com";
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");
        mockAccount.setId(1L);

        when(accountRepository.findByEmail(mockEmail)).thenReturn(Optional.of(mockAccount));

        // Act
        long result = accountService.getAccountIdByEmail(mockEmail);

        // Assert
        Assertions.assertEquals(1L, result, "Account ID should match.");
    }

    @Test
    public void getAccountIdByEmail_NonExistingEmail_ShouldThrowException(){
        // Arrange
        String mockEmail = "non-existing@mail.com"; // Non-existing email

        when(accountRepository.findByEmail(mockEmail)).thenThrow(new AccountNotFoundException(mockEmail));

        // Act & Assert
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountIdByEmail(mockEmail);
        }, "Should Throw AccountNotFoundException.");
    }

    @Test
    public void getAccountByEmail_ExistingEmail_ShouldReturnAccount(){
        // Arrange
        String mockEmail = "mpalambonisi63@gmail.com";
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");

        when(accountRepository.findByEmail(mockEmail)).thenReturn(Optional.of(mockAccount));

        // Act
        Account result = accountService.getAccountByEmail(mockEmail);

        // Assert
        Assertions.assertAll("Account properties",
                () -> Assertions.assertEquals("mpalambonisi63@gmail.com", result.getEmail(), "Account email should match."),
                () -> Assertions.assertEquals("0000", result.getPassword(), "Account password should match.")
        );
    }

    @Test
    public void getAccountByEmail_NonExistingEmail_ShouldThrowException(){
        // Arrange
        String mockEmail = "non-existing@mail.com"; // Non-existing email

        when(accountRepository.findByEmail(mockEmail)).thenThrow(new AccountNotFoundException(mockEmail));

        // Act & Assert
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountByEmail(mockEmail);
        }, "Should throw AccountNotFoundException.");
    }

    @Test
    public void deleteAccount_ExistingId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 1L;

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    public void deleteAccount_NonExistingId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 99L; // Non-existing ID mock

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }

}
