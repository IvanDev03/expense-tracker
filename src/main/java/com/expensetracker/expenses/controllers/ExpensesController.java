package com.expensetracker.expenses.controllers;

import com.expensetracker.expenses.dto.CreateExpenseDTO;
import com.expensetracker.expenses.dto.UpdateExpenseDTO;
import com.expensetracker.expenses.entities.Expenses;
import com.expensetracker.expenses.services.ExpensesService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class ExpensesController {

    private final ExpensesService expensesService;

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }
    @PostMapping("/expenses")
    public ResponseEntity<CreateExpenseDTO> addExpense(@RequestBody CreateExpenseDTO createExpenseDTO) {
        return expensesService.addExpense(createExpenseDTO);
    }
    @GetMapping("/expenses")
    public ResponseEntity<List<Expenses>> getExpenses(@Param("page") Integer page, @Param("size") Integer size) {
        return expensesService.getExpenses(page, size);
    }
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expenses> getExpenseById(@PathVariable UUID id) {
        return expensesService.getExpenseById(id);
    }
    @PutMapping("/expenses/{id}")
    public Optional<Expenses> updateExpense(@PathVariable UUID id, @RequestBody UpdateExpenseDTO updateExpenseDTO) {
        return expensesService.updateExpense(id, updateExpenseDTO);
    }
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Expenses> deleteExpense(@PathVariable UUID id) {
        return expensesService.deleteExpense(id);
    }
    @GetMapping("/expenses/monthly-summary")
    public Double getMonthlyExpenseSummary(@RequestParam int month) {
        return expensesService.getMonthlyExpenseSummary(month);
    }
    @GetMapping("/expenses/category")
    public List<Expenses> getExpensesByCategory(@RequestParam String category) {
        return  expensesService.getExpensesByCategory(category);
    }
}
