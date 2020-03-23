package com.skrypnik.financy.service.impl

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.repo.IncomeRepo
import com.skrypnik.financy.service.StatisticService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import javax.annotation.Resource

@Service
class StatisticServiceImpl : StatisticService {
    @Resource
    lateinit var expenseRepo: ExpenseRepo

    @Resource
    lateinit var incomeRepo: IncomeRepo

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