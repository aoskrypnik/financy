package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface IncomeRepo : JpaRepository<Income, Long> {
    fun findByUserAndCreationDateBetween(user: User, from: LocalDate, to: LocalDate): List<Income>

    fun findFirstByUserOrderByCreationDateAsc(user: User): Income?

    fun findFirstByUserOrderByCreationDateDesc(user: User): Income?
}