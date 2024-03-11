package com.example.JwtApi.Service;


import com.example.JwtApi.DTO.CreateUserDTO;
import com.example.JwtApi.DTO.LoginUserDTO;
import com.example.JwtApi.DTO.RecoveryJwtTokenDTO;
import com.example.JwtApi.Entity.Role;
import com.example.JwtApi.Entity.User;
import com.example.JwtApi.Repository.UserRepository;
import com.example.JwtApi.Security.Authentication.JwtTokenService;
import com.example.JwtApi.Security.config.SecurityConfiguration;
import com.example.JwtApi.Service.Impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um joken JWT

    public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO){
        // cria um objeto de autenticação com o email e a sneha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDTO createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .Roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }


}

















