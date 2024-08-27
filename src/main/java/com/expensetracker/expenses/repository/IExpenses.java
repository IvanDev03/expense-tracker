package com.expensetracker.expenses.repository;

import com.expensetracker.expenses.entities.Expenses;
import com.expensetracker.expenses.enums.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IExpenses extends JpaRepository<Expenses, UUID> {
    @Query("SELECT e FROM Expenses e WHERE YEAR(e.createdAt) = :year AND MONTH(e.createdAt) = :month")
    List<Expenses> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
    @Query("SELECT e FROM Expenses e WHERE e.category = :category")
    List<Expenses> getExpensesByCategory(@Param("category") Categories category);
}
