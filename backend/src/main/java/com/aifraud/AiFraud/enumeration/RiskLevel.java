package com.aifraud.AiFraud.enumeration;

public enum RiskLevel {
    HIGH("HIGH", "High risk - Immediate action required"),
    MEDIUM("MEDIUM", "Medium risk - Review recommended"),
    LOW("LOW", "Low risk - Safe transaction");
    
    private final String level;
    private final String message;
    
    RiskLevel(String level, String message) {
        this.level = level;
        this.message = message;
    }
    
    public String getLevel() { return level; }
    public String getMessage() { return message; }
}