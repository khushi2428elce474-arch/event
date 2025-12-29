package com.example.Expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Display all expenses (Home page)
    @GetMapping("/")
    public String homePage(Model model) {
        List<Expense> expenses = expenseRepository.findAll();
        model.addAttribute("expenses", expenses);

        // Calculate total amount
        double totalAmount = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        model.addAttribute("totalAmount", totalAmount);

        return "index";
    }

    // Show form to add expense (handled by POST method)
    // Note: The form is already on index.html, so we don't need separate GET method

    // Handle form submission to add expense
    @PostMapping("/expenses/add")
    public String addExpense(@RequestParam String description,
                             @RequestParam Double amount,
                             @RequestParam String category) {
        Expense expense = new Expense(description, amount, category);
        expenseRepository.save(expense);
        return "redirect:/";
    }

    // Show edit expense form
    @GetMapping("/expenses/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense id: " + id));
        model.addAttribute("expense", expense);
        return "edit-expense";
    }

    // Handle update expense
    @PostMapping("/expenses/update/{id}")
    public String updateExpense(@PathVariable Long id,
                                @RequestParam String description,
                                @RequestParam Double amount,
                                @RequestParam String category) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense id: " + id));

        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setCategory(category);

        expenseRepository.save(expense);
        return "redirect:/";
    }

    // Delete expense
    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return "redirect:/";
    }
}
