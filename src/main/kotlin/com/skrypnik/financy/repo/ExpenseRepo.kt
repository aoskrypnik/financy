package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Expense
import org.springframework.data.jpa.repository.JpaRepository

interface ExpenseRepo : JpaRepository<Expense, Long>