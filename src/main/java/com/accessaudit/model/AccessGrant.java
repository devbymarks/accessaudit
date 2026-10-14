package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="access_grants")
public class AccessGrant {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String requester; @Column(nullable=false) private String email;
 @Column(nullable=false) private String systemName; @Column(nullable=false) private String profileName;
 @Column(nullable=false) private String status="ACTIVE"; @Column(nullable=false) private Instant grantedAt=Instant.now();
 @Column(nullable=false) private Instant reviewDueAt; @Column(nullable=false) private Long sourceRequestId;
 private Instant updatedAt=Instant.now();
 protected AccessGrant(){}
 public AccessGrant(String requester,String email,String systemName,String profileName,Long sourceRequestId){this.requester=requester;this.email=email;this.systemName=systemName;this.profileName=profileName;this.sourceRequestId=sourceRequestId;this.grantedAt=Instant.now();this.updatedAt=Instant.now();this.reviewDueAt=Instant.now().plusSeconds(90L*24*60*60);}
 @PreUpdate void touch(){updatedAt=Instant.now();}
 public Long getId(){return id;} public String getRequester(){return requester;} public String getEmail(){return email;} public String getSystemName(){return systemName;} public String getProfileName(){return profileName;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public Instant getGrantedAt(){return grantedAt;} public Instant getReviewDueAt(){return reviewDueAt;} public void setReviewDueAt(Instant v){reviewDueAt=v;} public Long getSourceRequestId(){return sourceRequestId;} public void setSourceRequestId(Long v){sourceRequestId=v;} public void setProfileName(String v){profileName=v;}
}
