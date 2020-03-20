package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.repo.IncomeRepo
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import javax.annotation.Resource

@RestController
@RequestMapping("statistic")
class StatisticController {
    @Resource
    lateinit var incomeRepo: IncomeRepo

    @Resource
    lateinit var expenseRepo: ExpenseRepo

    @GetMapping("/balance/{dateReceived}")
    fun getBalanceByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") dateReceived: LocalDate,
                          @AuthenticationPrincipal user: User): Int {
        val from = LocalDateTime.of(dateReceived.year, dateReceived.month, 1, 0, 0)
        val to = LocalDateTime.of(dateReceived.year, dateReceived.month + 1, 1, 0, 0)
        val incomesSum = incomeRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { i -> i.sum }
                .fold(0) { acc, next -> acc + next }
        val expensesSum = expenseRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { e -> e.sum }
                .fold(0) { acc, next -> acc + next }
        return incomesSum - expensesSum
    }
}