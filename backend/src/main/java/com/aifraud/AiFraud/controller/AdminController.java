package com.aifraud.AiFraud.controller;

import com.aifraud.AiFraud.model.Transaction;
import com.aifraud.AiFraud.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/fraud")
    public ResponseEntity<List<Transaction>> getFraudTransactions() {
        return ResponseEntity.ok(adminService.getFraudTransactions());
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<Transaction>> getFlaggedTransactions() {
        return ResponseEntity.ok(adminService.getFlaggedTransactions());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(adminService.getAllTransactions());
    }

    @PostMapping("/review/{id}")
    public ResponseEntity<Transaction> reviewTransaction(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String action = request.get("action");
        Transaction reviewed = adminService.reviewTransaction(id, action);
        return ResponseEntity.ok(reviewed);
    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTransactions", adminService.getTotalTransactions());
        stats.put("blockedTransactions", adminService.getBlockedCount());
        stats.put("approvedTransactions", adminService.getApprovedCount());
        stats.put("flaggedTransactions", adminService.getFlaggedCount());
        stats.put("totalUsers", 21);
        return ResponseEntity.ok(stats);
    }
}