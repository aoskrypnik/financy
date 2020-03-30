package com.skrypnik.financy.service.impl

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.enums.ExpenseCategoryEnum
import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.enums.IncomeCategoryEnum
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
        val firstExpenseCreationDate = expenseRepo.findFirstByUserOrderByCreationDateAsc(user)?.creationDate
        val firstIncomeCreationDate = incomeRepo.findFirstByUserOrderByCreationDateAsc(user)?.creationDate
        return chooseDate(firstExpenseCreationDate, firstIncomeCreationDate, true)
    }

    override fun getLastRecordDateByUser(user: User): LocalDate {
        val lastExpenseCreationDate = expenseRepo.findFirstByUserOrderByCreationDateDesc(user)?.creationDate
        val lastIncomeCreationDate = incomeRepo.findFirstByUserOrderByCreationDateDesc(user)?.creationDate
        return chooseDate(lastExpenseCreationDate, lastIncomeCreationDate, false)
    }

    override fun getExpensesByUserAndDate(user: User, date: LocalDate): Map<ExpenseCategoryEnum, List<Expense>> {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1).minusDays(1)
        return expenseRepo.findByUserAndCreationDateBetween(user, from, to).groupBy { it.category }
    }

    override fun getIncomesByUserAndDate(user: User, date: LocalDate): Map<IncomeCategoryEnum, List<Income>> {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1).minusDays(1)
        return incomeRepo.findByUserAndCreationDateBetween(user, from, to).groupBy { it.category }
    }

    override fun countBalanceByUserAndMonth(user: User, date: LocalDate): Int {
        val from = LocalDate.of(date.year, date.month, 1)
        val to = LocalDate.of(date.year, date.month + 1, 1).minusDays(1)
        val incomesSum = incomeRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { i -> i.sum }
                .fold(0) { acc, next -> acc + next }
        val expensesSum = expenseRepo.findByUserAndCreationDateBetween(user, from, to)
                .map { e -> e.sum }
                .fold(0) { acc, next -> acc + next }
        return incomesSum - expensesSum
    }

    private fun chooseDate(firstDate: LocalDate?, secondDate: LocalDate?,
                           earliestDate: Boolean): LocalDate {
        return if (firstDate != null && secondDate != null) {
            val condition = if (earliestDate) (firstDate < secondDate) else (firstDate > secondDate)
            if (condition) firstDate else secondDate
        } else if (firstDate == null && secondDate == null) {
            LocalDate.now()
        } else {
            (firstDate ?: secondDate) as LocalDate
        }
    }
}