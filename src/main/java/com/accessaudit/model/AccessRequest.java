package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="access_requests")
public class AccessRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String requester; @Column(nullable=false) private String email;
 @Column(nullable=false) private String systemName; @Column(nullable=false) private String accessRole;
 private String currentAccessRole;
 @Column(nullable=false,length=1000) private String justification;
 @Column(nullable=false) private String requestType; @Column(nullable=false) private String status="PENDING";
 private String reviewer; @Column(length=1000) private String reviewNote;
 @Column(nullable=false) private Instant createdAt=Instant.now(); private Instant updatedAt=Instant.now(); private Instant decidedAt; private Instant reviewDueAt;
 @PreUpdate void touch(){updatedAt=Instant.now();}
 public Long getId(){return id;} public String getRequester(){return requester;} public void setRequester(String v){requester=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getSystemName(){return systemName;} public void setSystemName(String v){systemName=v;} public String getAccessRole(){return accessRole;} public void setAccessRole(String v){accessRole=v;} public String getCurrentAccessRole(){return currentAccessRole;} public void setCurrentAccessRole(String v){currentAccessRole=v;} public String getJustification(){return justification;} public void setJustification(String v){justification=v;} public String getRequestType(){return requestType;} public void setRequestType(String v){requestType=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getReviewer(){return reviewer;} public void setReviewer(String v){reviewer=v;} public String getReviewNote(){return reviewNote;} public void setReviewNote(String v){reviewNote=v;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public Instant getDecidedAt(){return decidedAt;} public void setDecidedAt(Instant v){decidedAt=v;} public Instant getReviewDueAt(){return reviewDueAt;} public void setReviewDueAt(Instant v){reviewDueAt=v;}
}
