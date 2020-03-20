package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface IncomeRepo : JpaRepository<Income, Long> {
    fun findByUserAndCreationDateBetween(user: User, from: LocalDateTime, to: LocalDateTime): List<Income>
}