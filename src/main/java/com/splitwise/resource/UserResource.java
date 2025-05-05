package com.splitwise.resource;


import com.splitwise.model.User;
import com.splitwise.service.UserService;
import com.splitwise.util.JwtUtil;
import io.dropwizard.hibernate.UnitOfWork;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService){
        this.userService = userService;
    }

    @POST
    @Path("/register")
    @UnitOfWork
    public Response registerUser(Map<String, String> request){
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");

        User user = userService.registerUser(name, email, password);
        return Response.status(Status.CREATED).entity(user).build();
    }

    @POST
    @Path("/login")
    @UnitOfWork
    public Response loginUser(Map<String, String> request){
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> user = userService.authenticateUser(email, password);
        if(user.isPresent()){
            String token = JwtUtil.generateToken(user.get().getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();
        }else{
            return Response.status(Status.UNAUTHORIZED).entity("Invalid Credentials").build();
        }
    }

    @GET
    @UnitOfWork
    public Response getUsers(){
        List<User> users = userService.getAllUsers();
        return Response.ok(users).build();
    }
}
