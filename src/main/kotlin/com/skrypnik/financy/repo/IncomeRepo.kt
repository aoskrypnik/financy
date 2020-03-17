package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.Income
import org.springframework.data.jpa.repository.JpaRepository

interface IncomeRepo : JpaRepository<Income, Long>