package com.aux.spring_app.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aux.spring_app.user.CustomUserDetails;
import com.aux.spring_app.user.Role;
import com.aux.spring_app.user.User;

import jakarta.transaction.Transactional;

public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userService.findUserByUserName(userName);
    List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
    return buildUserForAuthentication(user, authorities);
  }

  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
    Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
    for (Role role : userRoles) {
      roles.add(new SimpleGrantedAuthority(role.getRole()));
    }
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
    return grantedAuthorities;
  }

  private CustomUserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    return new CustomUserDetails.Builder()
    .withEmail(user.getEmail())
    .withUsername(user.getUserName())
    .withProfileImg(user.getImg())
    .withPassword(user.getPassword())
    .withAuthorities(getUserAuthority(user.getRoles()))
    .build();
  }
}
