package com.aifraud.AiFraud.repository;

import com.aifraud.AiFraud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatus(String status);

    List<Transaction> findBySenderId(String senderId);

    long countByStatus(String status);

    @Query("SELECT COUNT(DISTINCT t.senderId) FROM Transaction t")
    long countDistinctUsers();
}