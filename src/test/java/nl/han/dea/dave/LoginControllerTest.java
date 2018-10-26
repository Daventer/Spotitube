package nl.han.dea.dave;

import nl.han.dea.dave.controllers.LoginController;
import nl.han.dea.dave.dto.UserDTO;
import nl.han.dea.dave.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class LoginControllerTest {

    private UserService userService;
    private LoginController loginController;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        userDTO = new UserDTO();
        loginController = new LoginController();
        userService = Mockito.mock(UserService.class);
        loginController.setUserService(userService);
        userDTO.setUser("Dave");
        userDTO.setPassword("1234");
    }

    @Test
    public void checkAuthentication() {
        Mockito.when(userService.authentication("Dave", "1234")).thenReturn(true);

        Response test = loginController.login(userDTO);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkFalseAuthentication(){
        Mockito.when(userService.authentication(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        Response test = loginController.login(userDTO);

        Assertions.assertEquals(401, test.getStatus());
    }

}
