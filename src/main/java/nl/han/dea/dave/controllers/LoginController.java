package nl.han.dea.dave.controllers;

import net.moznion.random.string.RandomStringGenerator;
import nl.han.dea.dave.dto.UserDTO;
import nl.han.dea.dave.dto.UserResponseDTO;
import nl.han.dea.dave.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    private UserService userService;
    private RandomStringGenerator generator = new RandomStringGenerator();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        String userName = userDTO.getUser();
        String password = userDTO.getPassword();

        if(userService.authentication(userName, password)){
            String token = generator.generateFromPattern("nnnn.nnnn.nnnn");
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setUser(userDTO.getUser());
            userResponseDTO.setToken(token);
            userService.setNewToken(userName, token);

            return Response.ok(userResponseDTO).build();
        }
        return Response.status(401).build();
    }

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
