package com.aifraud.AiFraud.service;

import com.aifraud.AiFraud.model.Transaction;
import com.aifraud.AiFraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    public List<Transaction> getFraudTransactions() {
        return transactionRepository.findByStatus("BLOCKED");
    }

    public List<Transaction> getFlaggedTransactions() {
        return transactionRepository.findByStatus("FLAGGED");
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction reviewTransaction(Long id, String action) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            if ("APPROVE".equalsIgnoreCase(action)) {
                transaction.setStatus("APPROVED");
                transaction.setReason("Admin approved after review");
                transaction.setRiskLevel("LOW");
            } else if ("BLOCK".equalsIgnoreCase(action)) {
                transaction.setStatus("BLOCKED");
                transaction.setReason("Admin blocked after review");
                transaction.setRiskLevel("HIGH");
            } else if ("FLAG".equalsIgnoreCase(action)) {
                transaction.setStatus("FLAGGED");
                transaction.setReason("Admin flagged for review");
                transaction.setRiskLevel("MEDIUM");
            }
            transaction.setReviewedBy("admin");
            transaction.setReviewedAt(LocalDateTime.now());
            transaction.setUpdatedAt(LocalDateTime.now());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public long getTotalTransactions() {
        return transactionService.getTotalTransactions();
    }

    public long getBlockedCount() {
        return transactionService.getBlockedCount();
    }

    public long getApprovedCount() {
        return transactionService.getApprovedCount();
    }

    public long getFlaggedCount() {
        return transactionService.getFlaggedCount();
    }
}