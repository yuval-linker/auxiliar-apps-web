package com.aux.spring_app.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aux.spring_app.user.Role;
import com.aux.spring_app.user.RoleRepository;
import com.aux.spring_app.user.User;
import com.aux.spring_app.user.UserRepository;

@Service
public class UserService {
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final String pathStatic = "./spring_app/src/main/resources/static";

  @Autowired
  public UserService(
      UserRepository userRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findUserByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  
  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    user.setProfileImg("/svg/anonymous.svg");
    Role userRole = roleRepository.findByRole("ADMIN");
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    return userRepository.save(user);
  }

  public String changeProfilePicture(String username, MultipartFile newImg) throws Exception {
    String _originalFilename = newImg.getOriginalFilename();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(_originalFilename.getBytes("UTF-8"));
    byte[] hash = md.digest();
    try (Formatter formatter = new Formatter()) {
      for (byte b : hash) {
        formatter.format("%02x", b);
      }

      String _filename = formatter.toString();
      String _extension = _originalFilename.substring(_originalFilename.lastIndexOf(".") + 1);

      String imgFilename = _filename + "." + _extension;
      String relativePathImg = "/uploads/" + imgFilename;
      String finalPath = pathStatic + relativePathImg;

      // 2. save img as a file
      Path path = Paths.get(finalPath);
      Files.copy(newImg.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
      User user = userRepository.findByUserName(username);
      user.setProfileImg(relativePathImg);
      userRepository.save(user);
      return relativePathImg;
    }
  }
}
