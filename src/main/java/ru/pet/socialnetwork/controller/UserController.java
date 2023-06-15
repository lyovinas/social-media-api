package ru.pet.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.socialnetwork.config.jwt.JWTTokenUtil;
import ru.pet.socialnetwork.dto.LoginDTO;
import ru.pet.socialnetwork.dto.UserDTO;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.service.UserService;
import ru.pet.socialnetwork.service.userdetails.CustomUserDetailsService;

import java.util.HashMap;
import java.util.Map;

import static ru.pet.socialnetwork.constants.UserRolesConstants.ADMIN;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями сервиса")
public class UserController extends GenericController<User, UserDTO> {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;


    public UserController(UserService userService,
                          CustomUserDetailsService customUserDetailsService,
                          JWTTokenUtil jwtTokenUtil
    ) {
        service = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    @Operation(hidden = true)
    public ResponseEntity<UserDTO> create(UserDTO newDTO) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/auth")
    @Operation(summary = "Аутентификация",
            description = "Позволяет войти в систему по переданным учетным данным")
    public ResponseEntity<?> auth(@RequestBody LoginDTO loginDTO) {

        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDTO.getLogin());
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Пользователь с указанным именем не найден");
        }
        if (!((UserService) service).checkPassword(loginDTO.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Ошибка авторизации!\n Неверный пароль!");
        }

        String token = jwtTokenUtil.generateToken(foundUser);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация",
            description = "Позволяет зарегистрироваться в системе")
    public ResponseEntity<?> registration(@RequestBody UserDTO userDTO) {
        if (ADMIN.equals(userDTO.getLogin())
                || ((UserService) service).getByLogin(userDTO.getLogin()) != null
                || ((UserService) service).getByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(service.create(userDTO));
    }
}
