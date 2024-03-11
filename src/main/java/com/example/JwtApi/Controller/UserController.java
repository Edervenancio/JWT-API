package com.example.JwtApi.Controller;


import com.example.JwtApi.DTO.CreateUserDTO;
import com.example.JwtApi.DTO.LoginUserDTO;
import com.example.JwtApi.DTO.RecoveryJwtTokenDTO;
import com.example.JwtApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDTO){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest(){
        return new ResponseEntity<>("Autenticado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest(){
        return new ResponseEntity<>("Cliente autenticado com sucesso!", HttpStatus.CREATED);
    }


    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}