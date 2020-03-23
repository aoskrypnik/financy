package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.service.StatisticService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.annotation.Resource

@RestController
@RequestMapping("statistic")
class StatisticController {
    @Resource
    lateinit var statisticService: StatisticService

    @GetMapping("/balance/{date}")
    fun getBalanceByMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
                          @AuthenticationPrincipal user: User): Int =
            statisticService.countBalanceByUserAndMonth(user, date)
}