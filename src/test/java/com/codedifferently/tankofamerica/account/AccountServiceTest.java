package com.codedifferently.tankofamerica.account;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repos.AccountRepo;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.NonSufficientFundsException;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountRepo accountRepo;
    private Account account;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Sabrina", "Krueger", "email@gmail.com", "Passw0rd!");
        user.setId(1L);
        account = new Account("checking main", user);
        account.setId(UUID.fromString("1eb2c8c2-902c-437e-954f-e5487bf1dd5d"));
    }

    @Test
    public void createAccountTest01() throws UserNotFoundException {
        BDDMockito.doReturn(account).when(accountRepo).save(account);
        Account actual = accountService.create(1L, account);
        Assertions.assertEquals(account, actual);
    }

    @Test
    public void getAccountByIdTest01() throws AccountNotFoundException {
        UUID id = account.getId();
        BDDMockito.doReturn(Optional.of(account)).when(accountRepo).findById(id);
        Account actual = accountService.getById("1eb2c8c2-902c-437e-954f-e5487bf1dd5d");
        Assertions.assertEquals(account, actual);
    }

    @Test
    public void getAccountByIdTest02() {
        UUID id = account.getId();
        BDDMockito.doReturn(Optional.empty()).when(accountRepo).findById(id);
        Assertions.assertThrows(AccountNotFoundException.class, ()->accountService.getById("1eb2c8c2-902c-437e-954f-e5487bf1dd5d"));
    }

    @Test
    public void getAllUserAccountsTest01() throws UserNotFoundException{
        String expected = "Account for Sabrina named checking main with id 1eb2c8c2-902c-437e-954f-e5487bf1dd5d and balance $0.00";
        System.out.println("expected:"+expected);

        BDDMockito.doReturn(account).when(accountRepo).save(account);
        accountService.create(1L, account);
        String actual = accountService.getAllFromUser(1L);
        System.out.println("actual:"+actual);
        //Assertions.assertEquals(expected,actual);
        //how to do..
    }

    @Test
    public void updateAccountTest01() {
        account.setBalance(250.00);
        BDDMockito.doReturn(account).when(accountRepo).save(account);
        Account actual = accountService.update(account);
        Assertions.assertEquals(account, actual);
    }

    @Test
    public void updateBalanceTest01() throws NonSufficientFundsException {
        account.updateBalance(250.00);
        Double expected = 250.00;
        Double actual = account.getBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateBalanceTest02() {
        Assertions.assertThrows(NonSufficientFundsException.class, ()-> account.updateBalance(-25.00));
    }
}