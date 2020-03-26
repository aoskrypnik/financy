package com.skrypnik.financy.service.impl

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.repo.IncomeRepo
import com.skrypnik.financy.service.RecordService
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.annotation.Resource

@Service
class RecordServiceImpl : RecordService {
    @Resource
    lateinit var expenseRepo: ExpenseRepo

    @Resource
    lateinit var incomeRepo: IncomeRepo

    override fun getFirstRecordDateByUser(user: User): LocalDate {
        val firstExpenseCreationDate = expenseRepo.findFirstByUserOrderByCreationDateAsc(user).creationDate
        val firstIncomeCreationDate = incomeRepo.findFirstByUserOrderByCreationDateAsc(user).creationDate
        return if (firstExpenseCreationDate < firstIncomeCreationDate) {
            firstExpenseCreationDate
        } else {
            firstIncomeCreationDate
        }
    }

    override fun getLastRecordDateByUser(user: User): LocalDate {
        val lastExpenseCreationDate = expenseRepo.findFirstByUserOrderByCreationDateDesc(user).creationDate
        val lastIncomeCreationDate = incomeRepo.findFirstByUserOrderByCreationDateDesc(user).creationDate
        return if (lastExpenseCreationDate > lastIncomeCreationDate) {
            lastExpenseCreationDate
        } else {
            lastIncomeCreationDate
        }
    }

    override fun getExpensesByUserAndDate(user: User, date: LocalDate): List<Expense> {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1)
        return expenseRepo.findByUserAndCreationDateBetween(user, from, to)
    }

    override fun getIncomesByUserAndDate(user: User, date: LocalDate): List<Income> {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1)
        return incomeRepo.findByUserAndCreationDateBetween(user, from, to)
    }

    override fun countBalanceByUserAndMonth(user: User, date: LocalDate): Int {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1)
        val incomesSum = incomeRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { i -> i.sum }
                .fold(0) { acc, next -> acc + next }
        val expensesSum = expenseRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { e -> e.sum }
                .fold(0) { acc, next -> acc + next }
        return incomesSum - expensesSum
    }
}