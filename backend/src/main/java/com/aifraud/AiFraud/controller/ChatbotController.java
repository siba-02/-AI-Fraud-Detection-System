package com.aifraud.AiFraud.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {
    
    private final Random random = new Random();
    
    @PostMapping("/ask")
    public Map<String, String> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question").toLowerCase();
        Map<String, String> response = new HashMap<>();
        
        if (question.contains("fraud")) {
            response.put("answer", "🚨 Fraud Detection Tips:\n• Watch for transactions above ₹50,000\n• Check suspicious locations\n• Monitor multiple rapid transactions\n• Verify unusual patterns");
        } else if (question.contains("transaction") && question.contains("safe")) {
            response.put("answer", "✅ Safe Transaction Tips:\n• Use strong passwords\n• Enable 2FA\n• Review history regularly\n• Never share OTP");
        } else if (question.contains("block")) {
            response.put("answer", "🚫 Transactions are blocked when amount exceeds ₹70,000 or suspicious patterns detected.");
        } else if (question.contains("limit")) {
            response.put("answer", "💰 Transaction Limits:\n• SAFE: < ₹10,000\n• FLAGGED: ₹10,000 - ₹70,000\n• BLOCKED: > ₹70,000");
        } else if (question.contains("hello") || question.contains("hi")) {
            response.put("answer", "👋 Hello! I'm your AI Fraud Assistant. How can I help?");
        } else {
            response.put("answer", "🤖 I'm your AI Fraud Assistant. Ask me about fraud detection, transaction limits, or security tips!");
        }
        
        return response;
    }
}