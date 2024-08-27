package com.expensetracker.expenses.dto;

import com.expensetracker.expenses.enums.Categories;

import java.util.Optional;

public record UpdateExpenseDTO(Optional<String> description,
                               Optional<Float> amount,
                               Optional<Categories> category) {
}
