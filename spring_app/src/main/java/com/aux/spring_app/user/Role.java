package com.aux.spring_app.user;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Role(String role) {
      this.role = role;
    }

    public String getRole() {
      return null;
    }
}