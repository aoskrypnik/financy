package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Expense
import com.skrypnik.financy.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ExpenseRepo : JpaRepository<Expense, Long> {
    fun findByUserAndCreationDateBetween(user: User, from: LocalDate, to: LocalDate): List<Expense>

    fun findFirstByUserOrderByCreationDateAsc(user: User): Expense

    fun findFirstByUserOrderByCreationDateDesc(user: User): Expense
}