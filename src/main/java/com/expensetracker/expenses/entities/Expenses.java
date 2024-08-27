package com.expensetracker.expenses.entities;

import com.expensetracker.expenses.dto.CreateExpenseDTO;
import com.expensetracker.expenses.enums.Categories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;
@Entity
@Table(name = "expenses")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Expenses {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    @Column
    private String description;
    @Column
    private Float amount;
    @CreationTimestamp
    @Column
    private Timestamp createdAt;
    @Column
    @Enumerated(EnumType.STRING)
    private Categories category;

    public Expenses(CreateExpenseDTO createExpenseDTO) {
        this.description = createExpenseDTO.description();
        this.amount = createExpenseDTO.amount();
        this.createdAt = createExpenseDTO.createdAt();
        this.category = createExpenseDTO.category();
    }
}
