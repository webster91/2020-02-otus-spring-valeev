package ru.otus.valeev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.valeev.dto.UserDto;
import ru.otus.valeev.security.UserPrincipal;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/api/auth")
    public ResponseEntity<UserDto> auth() {
        log.info("Получение UserDto");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        UserDto userDto = UserDto.builder()
                .id(userDetails.getId())
                .name(userDetails.getUsername())
                .login(userDetails.getUsername())
                .roles(AuthorityUtils.authorityListToSet(userDetails.getAuthorities()))
                .build();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/api/logOut")
    public ResponseEntity<Void> logOut(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
