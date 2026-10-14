package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="admin_accounts", uniqueConstraints={@UniqueConstraint(columnNames="username"),@UniqueConstraint(columnNames="email")})
public class AdminAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String username; @Column(nullable=false) private String passwordHash;
 @Column(nullable=false) private String fullName; @Column(nullable=false) private String email; @Column(nullable=false) private String role; @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected AdminAccount(){}
 public AdminAccount(String username,String passwordHash,String fullName,String email,String role,boolean active){this.username=username;this.passwordHash=passwordHash;this.fullName=fullName;this.email=email;this.role=role;this.active=active;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getPasswordHash(){return passwordHash;} public String getFullName(){return fullName;} public String getEmail(){return email;} public String getRole(){return role;} public boolean isActive(){return active;} public void setPasswordHash(String v){passwordHash=v;} public void setFullName(String v){fullName=v;} public void setRole(String v){role=v;} public void setActive(boolean v){active=v;}
}
