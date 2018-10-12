package nl.han.dea.dave;

import nl.han.dea.dave.controllers.dto.UserDTO;
import nl.han.dea.dave.datasource.daos.UserDao;
import nl.han.dea.dave.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {

    private UserService userService;
    private final String USERNAME = "Dave" ;
    private final String PASSWORD = "1234" ;
    private final String TOKEN = "1234-1234-1234";

    @BeforeEach
    public void setup(){
        userService = new UserService();
        UserDao userDao = Mockito.mock(UserDao.class);
        userService.setUserDao(userDao);

        // UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(USERNAME);
        userDTO.setPassword(PASSWORD);

        // getUser
        Mockito.when(userDao.getUser(USERNAME)).thenReturn(userDTO);

        // getUserIdFromToken
        Mockito.when(userDao.getUserIdFromToken(TOKEN)).thenReturn(1);

        // get password from user
        Mockito.when(userDao.getUser(USERNAME)).thenReturn(userDTO);
    }

    @Test
    public void checkAuthenticationIsTrue(){
        Boolean authentication = userService.authentication(USERNAME, PASSWORD);

        Assertions.assertEquals(true, authentication);
    }

    @Test
    public void checkIfAuthenticationIsFalse(){
        Boolean authentication = userService.authentication(USERNAME, "123");

        Assertions.assertEquals(false, authentication);
    }

    @Test
    public void checkIfUserDoesntExists(){
        Boolean authentication = userService.authentication("pieter", PASSWORD);

        Assertions.assertEquals(false, authentication);
    }

    @Test
    public void checkIfTokenIsValid(){
        Boolean authentication = userService.tokenIsValid(TOKEN);

        Assertions.assertEquals(true, authentication);
    }

    @Test
    public void checkIfTokenIsInvalid(){
        Boolean authentication = userService.tokenIsValid("123");

        Assertions.assertEquals(false, authentication);
    }

    @Test
    public void checkIfUserIdEquelsOne(){
        int userId = userService.getUserIdFromToken(TOKEN);

        Assertions.assertEquals(1, userId);
    }

    @Test
    public void checkIfTokenParamIsInvalidForGetUserIdFromToken(){
        int userId = userService.getUserIdFromToken("");

        Assertions.assertEquals(0, userId);
    }

    @Test
    public void checkIfUserExistReturnsTrue(){
        Boolean userExists = userService.userExist(USERNAME);

        Assertions.assertEquals(true, userExists);
    }

    @Test
    public void checkIfUserExistReturnsFalse(){
        Boolean userExists = userService.userExist("pieter");

        Assertions.assertEquals(false, userExists);
    }

    @Test
    public void checkIfUserPasswordEqualsFinalPassword(){
        String password = userService.getPasswordFromUser(USERNAME);

        Assertions.assertEquals(PASSWORD, password);
    }
}
