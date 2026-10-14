package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="managed_systems", uniqueConstraints=@UniqueConstraint(columnNames="code"))
public class ManagedSystem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; @Column(nullable=false) private String code;
 @Column(length=1000) private String description; @Column(nullable=false) private String ownerTeam;
 @Column(nullable=false) private String criticality; @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected ManagedSystem(){} public ManagedSystem(String name,String code,String description,String ownerTeam,String criticality,boolean active){this.name=name;this.code=code;this.description=description;this.ownerTeam=ownerTeam;this.criticality=criticality;this.active=active;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getCode(){return code;} public void setCode(String v){code=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getOwnerTeam(){return ownerTeam;} public void setOwnerTeam(String v){ownerTeam=v;} public String getCriticality(){return criticality;} public void setCriticality(String v){criticality=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public Instant getCreatedAt(){return createdAt;}
}
