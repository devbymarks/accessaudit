package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="access_profiles")
public class AccessProfile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; @Column(length=1000) private String description;
 @Column(nullable=false) private String systemName; @Column(length=1500,nullable=false) private String permissionsText;
 @Column(nullable=false) private String riskLevel; @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected AccessProfile(){} public AccessProfile(String name,String description,String systemName,String permissionsText,String riskLevel,boolean active){this.name=name;this.description=description;this.systemName=systemName;this.permissionsText=permissionsText;this.riskLevel=riskLevel;this.active=active;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getSystemName(){return systemName;} public void setSystemName(String v){systemName=v;} public String getPermissionsText(){return permissionsText;} public void setPermissionsText(String v){permissionsText=v;} public String getRiskLevel(){return riskLevel;} public void setRiskLevel(String v){riskLevel=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public Instant getCreatedAt(){return createdAt;}
}
