package com.skrypnik.financy.config

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.repo.UserDetailsRepo
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.time.LocalDateTime

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().rememberMe()
                .and().csrf().disable()
    }

    @Bean
    fun principalExtractor(userDetailsRepo: UserDetailsRepo): PrincipalExtractor {
        return PrincipalExtractor {
            val id: String = it.getValue("sub") as String
            val user = userDetailsRepo.findById(id).orElseGet {
                User(
                        id = id,
                        name = it.getValue("name") as String,
                        email = it.getValue("email") as String,
                        locale = it.getValue("locale") as String
                )
            }
            user.lastVisit = LocalDateTime.now()
            return@PrincipalExtractor userDetailsRepo.save(user)
        }
    }
}