package com.skrypnik.financy.service

import com.skrypnik.financy.domain.User
import java.time.LocalDate

interface StatisticService {
    fun countBalanceByUserAndMonth(user: User, date: LocalDate): Int
}