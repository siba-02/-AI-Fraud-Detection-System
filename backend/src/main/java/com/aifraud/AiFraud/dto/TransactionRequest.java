package com.aifraud.AiFraud.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TransactionRequest {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
    
    @NotBlank(message = "Merchant is required")
    private String merchant;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    private String userName;
    private String userEmail;
}