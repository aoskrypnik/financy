package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.service.RecordService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.annotation.Resource

@RestController
@RequestMapping("record")
class RecordController {
    @Resource
    lateinit var recordService: RecordService

    @GetMapping("/balance/{date}")
    fun getBalanceByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
                          @AuthenticationPrincipal user: User): Double =
            recordService.countBalanceByUserAndMonth(user, date)

    @GetMapping("/{date}")
    fun getIncomesAndExpensesByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
                                     @AuthenticationPrincipal user: User) = mapOf(
            "incomes" to recordService.getIncomesByUserAndDate(user, date),
            "expenses" to recordService.getExpensesByUserAndDate(user, date)
    )

    @GetMapping("/bounds")
    fun getFirstAndLastRecordsByUser(@AuthenticationPrincipal user: User) = mapOf(
            "first" to recordService.getFirstRecordDateByUser(user),
            "last" to recordService.getLastRecordDateByUser(user)
    )
}