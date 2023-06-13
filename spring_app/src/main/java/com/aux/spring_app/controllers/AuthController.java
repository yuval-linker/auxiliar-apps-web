package com.aux.spring_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aux.spring_app.services.UserService;
import com.aux.spring_app.user.CustomUserDetails;
import com.aux.spring_app.user.User;

import jakarta.validation.Valid;

@Controller
public class AuthController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/register")
  public String registration(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "register";
  }

  @PostMapping("/register-save")
  public String createNewUser(@Valid User user, Model model, Errors errors) {
    if (errors.hasErrors()) {
      model.addAttribute("errors", errors);
      return "register";
    }
    userService.saveUser(user);
    return "redirect:/";
  }

  @GetMapping("/profile")
  public String profileRoute(Model model, @AuthenticationPrincipal CustomUserDetails user) {
    model.addAttribute("user", user);
    return "profile";
  }

  @PostMapping("/profile")
  public String changePicture(@RequestParam("profile-img") MultipartFile picture, @AuthenticationPrincipal CustomUserDetails user) throws Exception {
    String newImg = userService.changeProfilePicture(user.getUsername(), picture);
    // Update user in Context token
    user.setProfileImg(newImg);
    return "redirect:profile";
  }
}
