package com.aux.spring_app.user;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @Column(name = "user_id")
  private Long id;

  @Length(min = 4, message = "*Su nobre de usuario debe tener al menos 4 caracteres")
  @NotEmpty(message = "*Debe tener un nombre de usuario")
  @Column(name = "username")
  private String userName;

  @Email(message = "*Su correo no es valido")
  @NotEmpty(message = "*Debe rellenar con un correo")
  @Column(name = "email")
  private String email;

  @Length(min = 5, message = "*Su contraseña debe tener al menos 5 caracteres")
  @NotEmpty(message = "*Debe tener una contraseña")
  @Column(name = "password")
  private String password;

  @Column(name = "profile_img")
  private String img;

  @Column(name = "active")
  private Boolean active;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public Long getId() {
    return id;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Boolean getActive() {
    return active;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getImg() {
    return img;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User() {
  }

  public User(String userName,
      String email,
      String password,
      String profileImg,
      Boolean active,
      Set<Role> roles) {

    this.userName = userName;
    this.email = email;
    this.password = password;
    this.img = profileImg;
    this.active = active;
    this.roles = roles;
  }

  public void setUserName(String username) {
    this.userName = username;
  }

  public void setEmail(String string) {
    this.email = string;
  }

  public void setProfileImg(String pathToImg) {
    this.img = pathToImg;
  }
}