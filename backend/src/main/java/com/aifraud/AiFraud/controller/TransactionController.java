package com.aifraud.AiFraud.controller;

import com.aifraud.AiFraud.model.Transaction;
import com.aifraud.AiFraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.addTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(transactionService.getTransactionsByStatus(status));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable String username) {
        return ResponseEntity.ok(transactionService.getUserTransactions(username));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", transactionService.getTotalTransactions());
        stats.put("blocked", transactionService.getBlockedCount());
        stats.put("approved", transactionService.getApprovedCount());
        stats.put("flagged", transactionService.getFlaggedCount());
        stats.put("users", transactionService.getTotalUsers());
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Transaction> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        Transaction updated = transactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}