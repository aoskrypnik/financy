package com.skrypnik.financy.service

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import java.time.LocalDate

interface RecordService {
    fun getFirstRecordDateByUser(user: User): LocalDate

    fun getLastRecordDateByUser(user: User): LocalDate

    fun getExpensesByUserAndDate(user: User, date: LocalDate): List<Expense>

    fun getIncomesByUserAndDate(user: User, date: LocalDate): List<Income>

    fun countBalanceByUserAndMonth(user: User, date: LocalDate): Int
}