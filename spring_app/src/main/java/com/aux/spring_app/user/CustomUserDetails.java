package com.aux.spring_app.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class CustomUserDetails extends User {

  private final String email;
  private final String username;
  private String profileImg;

  private CustomUserDetails(Builder builder) {
    super(builder.username, builder.password, builder.authorities);
    this.username = builder.username;
    this.profileImg = builder.profileImg;
    this.email = builder.email;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getProfileImg() {
    return profileImg;
  }

  public void setProfileImg(String img) {
    this.profileImg = img;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    CustomUserDetails that = (CustomUserDetails) o;
    return username.equals(that.username) && profileImg.equals(that.profileImg) && email.equals(that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), username, profileImg, email);
  }

  public static class Builder {

    private String email;
    private String username;
    private String password;
    private String profileImg;
    private Collection<? extends GrantedAuthority> authorities;

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public Builder withProfileImg(String pathToImg) {
      this.profileImg = pathToImg;
      return this;
    }

    public CustomUserDetails build() {
      return new CustomUserDetails(this);
    }
  }
}
