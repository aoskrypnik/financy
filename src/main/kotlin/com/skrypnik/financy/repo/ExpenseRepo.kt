package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ExpenseRepo : JpaRepository<Expense, Long> {
    fun findByUserAndCreationDateBetween(user: User, from: LocalDateTime, to: LocalDateTime): List<Expense>
}