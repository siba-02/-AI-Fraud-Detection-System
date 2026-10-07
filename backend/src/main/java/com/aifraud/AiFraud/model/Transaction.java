package com.aifraud.AiFraud.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String transactionId;

    private BigDecimal amount;
    private String currency;
    private String senderId;
    private String receiverId;
    private String senderLocation;
    private String receiverLocation;
    private String paymentMethod;
    private String deviceId;
    private String ipAddress;
    private LocalDateTime transactionTime;

    private Double riskScore;
    private String riskLevel;  // ADD THIS FIELD
    private Boolean fraudPrediction;
    private Double fraudProbability;
    private String status;
    private String reason;  // ADD THIS FIELD

    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewNotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Transaction() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Transaction(String transactionId, BigDecimal amount, String currency) {
        this();
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getSenderLocation() { return senderLocation; }
    public void setSenderLocation(String senderLocation) { this.senderLocation = senderLocation; }

    public String getReceiverLocation() { return receiverLocation; }
    public void setReceiverLocation(String receiverLocation) { this.receiverLocation = receiverLocation; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public LocalDateTime getTransactionTime() { return transactionTime; }
    public void setTransactionTime(LocalDateTime transactionTime) { this.transactionTime = transactionTime; }

    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) { this.riskScore = riskScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public Boolean getFraudPrediction() { return fraudPrediction; }
    public void setFraudPrediction(Boolean fraudPrediction) { this.fraudPrediction = fraudPrediction; }

    public Double getFraudProbability() { return fraudProbability; }
    public void setFraudProbability(Double fraudProbability) { this.fraudProbability = fraudProbability; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getReviewNotes() { return reviewNotes; }
    public void setReviewNotes(String reviewNotes) { this.reviewNotes = reviewNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}