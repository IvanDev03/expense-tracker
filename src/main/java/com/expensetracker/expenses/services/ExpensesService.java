package com.expensetracker.expenses.services;

import com.common.exceptions.CreateExpenseException;
import com.expensetracker.expenses.dto.CreateExpenseDTO;
import com.expensetracker.expenses.enums.Categories;
import com.expensetracker.expenses.repository.IExpenses;
import com.expensetracker.expenses.dto.UpdateExpenseDTO;
import com.expensetracker.expenses.entities.Expenses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@Service
public class ExpensesService {
    Logger logger = Logger.getLogger(ExpensesService.class.getName());

    private final IExpenses iExpensesRepository;

    public ExpensesService(IExpenses iExpensesRepository) {
        this.iExpensesRepository = iExpensesRepository;
    }

    public ResponseEntity<CreateExpenseDTO> addExpense(CreateExpenseDTO createExpenseDTO) {
        try {
            if (createExpenseDTO.description() == null || createExpenseDTO.amount() == null || createExpenseDTO.category() == null) {
                throw new CreateExpenseException("C-400", HttpStatus.BAD_REQUEST, "Description, amount and category is required");
            }
            iExpensesRepository.save(new Expenses(createExpenseDTO));
            return ResponseEntity.ok(new CreateExpenseDTO(createExpenseDTO.description(),
                    createExpenseDTO.amount(), createExpenseDTO.createdAt(), createExpenseDTO.category()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Expenses>> getExpenses(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Expenses> expenses = iExpensesRepository.findAll(pageable).getContent();
            if (expenses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(expenses);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Expenses> getExpenseById(UUID id) {
        try {
            Expenses expenses = iExpensesRepository.findById(id).orElse(null);
            if (expenses == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Expenses> updateExpense(UUID id, UpdateExpenseDTO updateExpenseDTO) throws RuntimeException {
        try {
            return Optional.ofNullable(iExpensesRepository.findById(id).map(expense -> {
                updateExpenseDTO.description().ifPresent(expense::setDescription);
                updateExpenseDTO.amount().ifPresent(expense::setAmount);
                updateExpenseDTO.category().ifPresent(expense::setCategory);
                return iExpensesRepository.save(expense);
            }).orElseThrow(() -> new RuntimeException("Expense not found with id: " + id)));
        } catch (RuntimeException ex) {
            logger.info("Error updating expense: " + ex.getMessage());
            return Optional.empty();
        }
    }

    public ResponseEntity<Expenses> deleteExpense(UUID id) {
        try {
            Expenses expenses = iExpensesRepository.findById(id).orElse(null);
            if (expenses == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            iExpensesRepository.delete(expenses);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Expenses> getExpensesByMonth(int month) {
        int currentYear = LocalDate.now().getYear();
        return iExpensesRepository.findByYearAndMonth(currentYear, month);
    }

    public Double getMonthlyExpenseSummary(int month) {
        List<Expenses> expenses = getExpensesByMonth(month);
        return expenses.stream().mapToDouble(Expenses::getAmount).sum();
    }

    public List<Expenses> getExpensesByCategory(String category) {
        try {
            Categories.valueOf(category);
            return iExpensesRepository.getExpensesByCategory(Categories.valueOf(category));
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }

    }
}
