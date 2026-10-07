package com.aifraud.AiFraud.utils;

import com.aifraud.AiFraud.model.Transaction;

public class FraudDetectionUtil {

    public static void analyze(Transaction transaction) {

        // Convert BigDecimal to double
        double amount = transaction.getAmount().doubleValue();

        if (amount > 50000) {
            transaction.setRiskLevel("HIGH");
            transaction.setReason("Very high transaction amount - potential fraud");
            transaction.setFraudPrediction(true);
            transaction.setFraudProbability(0.9);
            transaction.setStatus("BLOCKED");
        }
        else if (amount > 20000) {
            transaction.setRiskLevel("HIGH");
            transaction.setReason("High transaction amount - needs review");
            transaction.setFraudPrediction(true);
            transaction.setFraudProbability(0.7);
            transaction.setStatus("FLAGGED");
        }
        else if (amount > 10000) {
            transaction.setRiskLevel("MEDIUM");
            transaction.setReason("Moderate risk transaction");
            transaction.setFraudPrediction(false);
            transaction.setFraudProbability(0.4);
            transaction.setStatus("FLAGGED");
        }
        else {
            transaction.setRiskLevel("LOW");
            transaction.setReason("Safe transaction");
            transaction.setFraudPrediction(false);
            transaction.setFraudProbability(0.1);
            transaction.setStatus("APPROVED");
        }

        // Set risk score
        transaction.setRiskScore(transaction.getFraudProbability());
    }
}