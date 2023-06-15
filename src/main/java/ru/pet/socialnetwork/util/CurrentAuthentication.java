//package ru.pet.socialnetwork.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import ru.pet.socialnetwork.service.userdetails.CustomUserDetails;
//
//@Component
//public class CurrentAuthentication {
//
//    @Value("${spring.security.user.name}")
//    private String adminUsername;
//
//
//
//    public Long getUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken
//                || adminUsername.equals(authentication.getName())) {
//            return null;
//        }
//        return  ((CustomUserDetails) authentication.getPrincipal()).getUserId();
//    }
//}
