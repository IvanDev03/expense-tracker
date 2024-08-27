package com.expensetracker.expenses.dto;

import com.expensetracker.expenses.enums.Categories;

import java.sql.Timestamp;

public record CreateExpenseDTO(String description, Float amount, Timestamp createdAt, Categories category) {
}
