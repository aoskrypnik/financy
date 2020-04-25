package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.service.RecordService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate
import javax.annotation.Resource

@Controller
@RequestMapping("/")
class MainController {

    @Value("\${spring.profiles.active:prod}")
    lateinit var profile: String

    @Resource
    lateinit var recordService: RecordService

    @GetMapping
    fun getMainPage(model: Model, @AuthenticationPrincipal user: User?): String {
        val data = mutableMapOf<Any, Any?>()
        val currentDate = LocalDate.now()
        user?.let {
            data["profile"] = it
            data["incomes"] = recordService.getIncomesByUserAndDate(it, currentDate)
            data["expenses"] = recordService.getExpensesByUserAndDate(it, currentDate)
            data["balance"] = recordService.countBalanceByUserAndMonth(it, currentDate)
        }
        model.addAttribute("frontendData", data)
        model.addAttribute("isDevMode", "dev" == profile)
        return "index"
    }

}