package com.codedifferently.tankofamerica.user;

import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.repos.UserRepo;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.junit.jupiter.api.Assertions;
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

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void getByIdTest01() throws UserNotFoundException {
        // Given
        User mockUser = new User("Sabrina", "Krueger", "email@gmail.com", "Passw0rd!");
        mockUser.setId(1l);
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findById(1l);
        User actualUser = userService.getById(1l);
        Assertions.assertEquals(mockUser, actualUser);
    }

    @Test
    public void createTest01() {
        User mockUser = new User("Sabrina", "Krueger", "email@gmail.com", "Passw0rd!");
        mockUser.setId(1L);
        BDDMockito.doReturn(mockUser).when(userRepo).save(mockUser);
        User actual = userService.create(mockUser);
        Assertions.assertEquals(mockUser, actual);
    }

    @Test
    public void getAllUsersTest01() {
        User mockUser = new User("Sabrina", "Krueger", "email@gmail.com", "Passw0rd!");
        mockUser.setId(1L);
        Iterable<User> users = new ArrayList<>(List.of(mockUser));
        BDDMockito.doReturn(users).when(userRepo).findAll();
        String expected = "1 Sabrina Krueger email@gmail.com Passw0rd!";
        String actual = userService.getAllUsers();
        Assertions.assertEquals(expected, actual);
    }
}