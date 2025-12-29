package com.example.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // We can add custom query methods here if needed
    // For now, basic CRUD operations are provided by JpaRepository
}