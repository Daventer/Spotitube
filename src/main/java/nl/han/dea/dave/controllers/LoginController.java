package nl.han.dea.dave.controllers;

import nl.han.dea.dave.controllers.dto.UserDTO;
import nl.han.dea.dave.controllers.dto.UserResponseDTO;
import nl.han.dea.dave.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        if(userService.authentication(userDTO.getUser(), userDTO.getPassword())){
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setUser(userDTO.getUser());
            userResponseDTO.setToken("1234-1234-1234");

            return Response.ok(userResponseDTO).build();
        }
        return Response.status(401).build();
    }

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
