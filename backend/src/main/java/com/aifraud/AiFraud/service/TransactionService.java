package com.aifraud.AiFraud.service;

import com.aifraud.AiFraud.model.Transaction;
import com.aifraud.AiFraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction addTransaction(Transaction transaction) {
        System.out.println("=== SAVING TRANSACTION ===");
        System.out.println("Received transaction: " + transaction);

        // Generate transaction ID if not present
        if (transaction.getTransactionId() == null || transaction.getTransactionId().isEmpty()) {
            transaction.setTransactionId("TXN" + System.currentTimeMillis());
        }

        // Set creation time if not set
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }
        if (transaction.getTransactionTime() == null) {
            transaction.setTransactionTime(LocalDateTime.now());
        }

        // Set default status
        if (transaction.getStatus() == null) {
            transaction.setStatus("PENDING");
        }

        // Set default currency
        if (transaction.getCurrency() == null) {
            transaction.setCurrency("INR");
        }

        // Set default payment method
        if (transaction.getPaymentMethod() == null) {
            transaction.setPaymentMethod("ONLINE");
        }

        // Calculate risk score and detect fraud
        double riskScore = calculateRiskScore(transaction);
        transaction.setRiskScore(riskScore);

        // Set risk level and reason based on risk score
        if (riskScore > 0.8) {
            transaction.setRiskLevel("HIGH");
            transaction.setStatus("BLOCKED");
            transaction.setReason("🔴 HIGH RISK: Transaction blocked automatically (Score: " + String.format("%.2f", riskScore) + ")");
            transaction.setFraudPrediction(true);
        } else if (riskScore > 0.5) {
            transaction.setRiskLevel("MEDIUM");
            transaction.setStatus("FLAGGED");
            transaction.setReason("🟡 MEDIUM RISK: Transaction flagged for review (Score: " + String.format("%.2f", riskScore) + ")");
            transaction.setFraudPrediction(true);
        } else {
            transaction.setRiskLevel("LOW");
            transaction.setStatus("APPROVED");
            transaction.setReason("🟢 LOW RISK: Transaction approved (Score: " + String.format("%.2f", riskScore) + ")");
            transaction.setFraudPrediction(false);
        }

        transaction.setFraudProbability(riskScore);
        transaction.setUpdatedAt(LocalDateTime.now());

        System.out.println("Saving transaction with status: " + transaction.getStatus());
        System.out.println("Risk score: " + riskScore);

        Transaction saved = transactionRepository.save(transaction);
        System.out.println("Saved transaction ID: " + saved.getId());
        System.out.println("=== TRANSACTION SAVED SUCCESSFULLY ===");

        return saved;
    }

    private double calculateRiskScore(Transaction transaction) {
        Random random = new Random();
        double riskScore = 0.0;

        // Convert BigDecimal to double safely
        double amount = transaction.getAmount() != null ? transaction.getAmount().doubleValue() : 0;
        System.out.println("Calculating risk for amount: " + amount);

        // High amount risk
        if (amount > 50000) {
            riskScore += 0.5;
            System.out.println("  + High amount (>50k): +0.5");
        } else if (amount > 25000) {
            riskScore += 0.3;
            System.out.println("  + Medium-high amount (>25k): +0.3");
        } else if (amount > 10000) {
            riskScore += 0.15;
            System.out.println("  + Medium amount (>10k): +0.15");
        }

        // Location-based risk
        String location = transaction.getSenderLocation();
        if (location != null && !location.isEmpty()) {
            String locLower = location.toLowerCase();
            if (locLower.contains("foreign") || locLower.contains("international") ||
                    locLower.contains("abroad") || locLower.contains("usa") || locLower.contains("uk") ||
                    locLower.contains("dubai") || locLower.contains("singapore")) {
                riskScore += 0.3;
                System.out.println("  + International location: +0.3");
            }
        }

        // Payment method risk
        if (transaction.getPaymentMethod() != null) {
            String paymentMethod = transaction.getPaymentMethod().toUpperCase();
            if (paymentMethod.equals("CRYPTO") || paymentMethod.equals("BITCOIN")) {
                riskScore += 0.4;
                System.out.println("  + Crypto payment: +0.4");
            } else if (paymentMethod.equals("WIRE_TRANSFER")) {
                riskScore += 0.2;
                System.out.println("  + Wire transfer: +0.2");
            }
        }

        // Merchant risk
        String merchant = transaction.getReceiverId();
        if (merchant != null && !merchant.isEmpty()) {
            String merchantLower = merchant.toLowerCase();
            if (merchantLower.contains("casino") || merchantLower.contains("gambling") ||
                    merchantLower.contains("crypto") || merchantLower.contains("bitcoin")) {
                riskScore += 0.3;
                System.out.println("  + High-risk merchant: +0.3");
            }
        }

        // Add some randomness for demo
        double randomFactor = random.nextDouble() * 0.1;
        riskScore += randomFactor;
        System.out.println("  + Random factor: +" + String.format("%.2f", randomFactor));

        double finalScore = Math.min(riskScore, 1.0);
        System.out.println("Final risk score: " + String.format("%.2f", finalScore));

        return finalScore;
    }

    public List<Transaction> getAllTransactions() {
        System.out.println("Fetching all transactions");
        List<Transaction> transactions = transactionRepository.findAll();
        System.out.println("Found " + transactions.size() + " transactions");
        return transactions;
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        System.out.println("Fetching transactions with status: " + status);
        return transactionRepository.findByStatus(status);
    }

    public List<Transaction> getUserTransactions(String username) {
        System.out.println("Fetching transactions for user: " + username);
        return transactionRepository.findBySenderId(username);
    }

    public long getTotalTransactions() {
        return transactionRepository.count();
    }

    public long getBlockedCount() {
        return transactionRepository.countByStatus("BLOCKED");
    }

    public long getApprovedCount() {
        return transactionRepository.countByStatus("APPROVED");
    }

    public long getFlaggedCount() {
        return transactionRepository.countByStatus("FLAGGED");
    }

    public long getTotalUsers() {
        return transactionRepository.countDistinctUsers();
    }

    public Transaction updateTransactionStatus(Long id, String status) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setStatus(status);
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setReason("Transaction " + status.toLowerCase() + " by admin");
        return transactionRepository.save(transaction);
    }
}