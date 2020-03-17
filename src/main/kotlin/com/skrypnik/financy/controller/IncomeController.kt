package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.Income
import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.IncomeRepo
import com.skrypnik.financy.repo.UserDetailsRepo
import org.springframework.beans.BeanUtils.copyProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.annotation.Resource

@RestController
@RequestMapping("income")
class IncomeController {

    @Resource
    lateinit var incomeRepo: IncomeRepo

    @GetMapping
    fun getAll(): List<Income> = incomeRepo.findAll()

    @GetMapping("{id}")
    fun getIncomeById(@PathVariable("id") income: Income) = income

    @PostMapping
    fun createIncome(@RequestBody income: Income): Income {
        income.user = SecurityContextHolder.getContext().authentication.principal as User
        income.creationDate = LocalDateTime.now()
        return incomeRepo.save(income)
    }

    @PutMapping("{id}")
    fun updateIncome(@PathVariable("id") incomeFromDb: Income,
                     @RequestBody income: Income): Income {
        copyProperties(income, incomeFromDb, "id")
        return incomeRepo.save(incomeFromDb)
    }

    @DeleteMapping("{id}")
    fun deleteIncome(@PathVariable("id") income: Income) {
        incomeRepo.delete(income)
    }
}