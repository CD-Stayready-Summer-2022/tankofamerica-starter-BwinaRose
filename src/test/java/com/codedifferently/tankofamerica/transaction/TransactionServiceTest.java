package com.codedifferently.tankofamerica.transaction;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.repos.TransactionRepo;
import com.codedifferently.tankofamerica.domain.transaction.services.TransactionService;
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

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepo transactionRepo;

    private Account account;
    private User user;
    private Transaction trans;
    private Transaction trans2;

    @BeforeEach
    public void setUp() {
        user = new User("Sabrina", "Krueger", "email@gmail.com", "Passw0rd!");
        user.setId(1L);
        account = new Account("checking main", user);
        account.setId(UUID.fromString("1eb2c8c2-902c-437e-954f-e5487bf1dd5d"));
        trans = new Transaction(250.00, account);
        trans2 = new Transaction(-2500.00, account);
    }

    @Test
    public void createTest01() throws AccountNotFoundException {
        UUID id = UUID.fromString("1eb2c8c2-902c-437e-954f-e5487bf1dd5d");
        BDDMockito.doReturn(trans).when(transactionRepo).save(trans);
        Transaction actual = transactionService.createTransaction(id, trans);
        Assertions.assertEquals(trans, actual);
    }

    @Test
    public void getByIdTest01() throws TransactionNotFoundException {
        UUID id = UUID.fromString("1eb2c8c2-902c-437e-954f-e5487bf1dd5d");
        BDDMockito.doReturn(Optional.of(trans)).when(transactionRepo).findById(id);
        String actual = String.valueOf(transactionService.getTransactionById(id));
        Assertions.assertEquals(String.valueOf(trans), actual);
    }

    @Test
    public void getByIdTest02() {
        UUID id = UUID.fromString("1eb2c8c2-902c-437e-954f-e5487bf1dd5d");
        BDDMockito.doReturn(Optional.empty()).when(transactionRepo).findById(id);
        Assertions.assertThrows(TransactionNotFoundException.class,()->transactionService.getTransactionById(id));
    }
}