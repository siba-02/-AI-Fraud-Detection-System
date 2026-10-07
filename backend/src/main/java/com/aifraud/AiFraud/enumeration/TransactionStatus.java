package com.aifraud.AiFraud.enumeration;

public enum TransactionStatus {
    APPROVED("APPROVED", "Transaction approved successfully"),
    BLOCKED("BLOCKED", "Transaction blocked due to fraud suspicion"),
    FLAGGED("FLAGGED", "Transaction flagged for manual review");
    
    private final String code;
    private final String description;
    
    TransactionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() { return code; }
    public String getDescription() { return description; }
}