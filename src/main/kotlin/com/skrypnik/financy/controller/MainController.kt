package com.skrypnik.financy.controller

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.ExpenseRepo
import com.skrypnik.financy.repo.IncomeRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.Objects.nonNull
import javax.annotation.Resource

@Controller
@RequestMapping("/")
class MainController {

    @Value("\${spring.profiles.active}")
    lateinit var profile: String

    @Resource
    lateinit var expenseRepo: ExpenseRepo

    @Resource
    lateinit var incomeRepo: IncomeRepo

    @GetMapping
    fun getMainPage(model: Model, @AuthenticationPrincipal user: User?): String {
        val data = mutableMapOf<Any, Any?>()
        if (nonNull(user)) {
            data["profile"] = user
            data["incomes"] = incomeRepo.findAll()
            data["expenses"] = expenseRepo.findAll()
        }
        model.addAttribute("frontendData", data)
        model.addAttribute("isDevMode", "dev" == profile)
        return "index"
    }

}