package com.skrypnik.financy.service.impl

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.repo.IncomeRepo
import com.skrypnik.financy.service.RecordService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
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
        return if (firstExpenseCreationDate!! < firstIncomeCreationDate!!) {
            firstExpenseCreationDate.run { toLocalDate() }
        } else {
            firstIncomeCreationDate.run { toLocalDate() }
        }
    }

    override fun getLastRecordDateByUser(user: User): LocalDate {
        val lastExpenseCreationDate = expenseRepo.findFirstByUserOrderByCreationDateDesc(user).creationDate
        val lastIncomeCreationDate = incomeRepo.findFirstByUserOrderByCreationDateDesc(user).creationDate
        return if (lastExpenseCreationDate!! > lastIncomeCreationDate!!) {
            lastExpenseCreationDate.run { toLocalDate() }
        } else {
            lastIncomeCreationDate.run { toLocalDate() }
        }
    }

    override fun getExpensesByUserAndDate(user: User, date: LocalDate): List<Expense> {
        val from = LocalDateTime.of(date.year, date.month, 1, 0, 0)
        val to = LocalDateTime.of(date.year, date.month + 1, 1, 0, 0)
        return expenseRepo.findByUserAndCreationDateBetween(user, from, to)
    }

    override fun getIncomesByUserAndDate(user: User, date: LocalDate): List<Income> {
        val from = LocalDateTime.of(date.year, date.month, 1, 0, 0)
        val to = LocalDateTime.of(date.year, date.month + 1, 1, 0, 0)
        return incomeRepo.findByUserAndCreationDateBetween(user, from, to)
    }

    override fun countBalanceByUserAndMonth(user: User, date: LocalDate): Int {
        val from = LocalDateTime.of(date.year, date.month, 1, 0, 0)
        val to = LocalDateTime.of(date.year, date.month + 1, 1, 0, 0)
        val incomesSum = incomeRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { i -> i.sum }
                .fold(0) { acc, next -> acc + next }
        val expensesSum = expenseRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { e -> e.sum }
                .fold(0) { acc, next -> acc + next }
        return incomesSum - expensesSum
    }
}