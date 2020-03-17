package com.skrypnik.financy.service

import com.skrypnik.financy.domain.User

interface UserService {
    fun getCurrentUser(): User
}