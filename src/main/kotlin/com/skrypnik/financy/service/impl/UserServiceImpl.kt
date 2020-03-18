package com.skrypnik.financy.service.impl

import com.skrypnik.financy.domain.User
import com.skrypnik.financy.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun getCurrentUser(): User = SecurityContextHolder.getContext().authentication.principal as User
}