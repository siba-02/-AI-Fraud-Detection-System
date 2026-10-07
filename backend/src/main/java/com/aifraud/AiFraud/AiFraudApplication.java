package com.aifraud.AiFraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiFraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiFraudApplication.class, args);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   🚀 AI FRAUD DETECTION SYSTEM 🚀     ║");
        System.out.println("║   Backend running on port 8080       ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}