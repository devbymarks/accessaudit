package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="directory_users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class DirectoryUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String fullName;
 @Column(nullable=false) private String email;
 @Column(nullable=false) private String department;
 @Column(nullable=false) private String jobTitle;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected DirectoryUser(){}
 public DirectoryUser(String fullName,String email,String department,String jobTitle,boolean active){this.fullName=fullName;this.email=email;this.department=department;this.jobTitle=jobTitle;this.active=active;}
 public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;} public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public Instant getCreatedAt(){return createdAt;}
}
