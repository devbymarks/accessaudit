package com.accessaudit.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="audit_events")
public class AuditEvent {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long requestId; private String entityType="ACCESS_REQUEST"; private Long entityId;
 @Column(nullable=false) private String action; @Column(nullable=false) private String actor;
 @Column(length=1500) private String details; @Column(nullable=false) private Instant occurredAt=Instant.now();
 protected AuditEvent(){}
 public AuditEvent(Long requestId,String action,String actor,String details){this.requestId=requestId;this.entityType="ACCESS_REQUEST";this.entityId=requestId;this.action=action;this.actor=actor;this.details=details;}
 public AuditEvent(String entityType,Long entityId,String action,String actor,String details){this.requestId=entityId==null?0L:-Math.abs(entityId);this.entityType=entityType;this.entityId=entityId;this.action=action;this.actor=actor;this.details=details;}
 public Long getId(){return id;} public Long getRequestId(){return requestId;} public String getEntityType(){return entityType;} public Long getEntityId(){return entityId;} public String getAction(){return action;} public String getActor(){return actor;} public String getDetails(){return details;} public Instant getOccurredAt(){return occurredAt;}
}
