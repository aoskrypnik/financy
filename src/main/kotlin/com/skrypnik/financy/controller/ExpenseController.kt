package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.enums.ExpenseCategoryEnum
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.service.UserService
import org.springframework.beans.BeanUtils.copyProperties
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("expense")
class ExpenseController {

    @Resource
    lateinit var userService: UserService

    @Resource
    lateinit var expenseRepo: ExpenseRepo

    @GetMapping
    fun getAll(): List<Expense> = expenseRepo.findAll()

    @GetMapping("{id}")
    fun getExpenseById(@PathVariable("id") income: Expense) = income

    @PostMapping
    fun createExpense(@RequestBody expense: Expense): Expense {
        expense.user = userService.getCurrentUser()
        return expenseRepo.save(expense)
    }

    @PutMapping("{id}")
    fun updateExpense(@PathVariable("id") expenseFromDb: Expense,
                      @RequestBody expense: Expense): Expense {
        copyProperties(expense, expenseFromDb, "id")
        return expenseRepo.save(expenseFromDb)
    }

    @DeleteMapping("{id}")
    fun deleteExpense(@PathVariable("id") expense: Expense) = expenseRepo.delete(expense)

    @GetMapping("/category")
    fun getCategories(): List<ExpenseCategoryEnum> = ExpenseCategoryEnum.values().toList()
}