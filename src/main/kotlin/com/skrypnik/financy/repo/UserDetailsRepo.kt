package com.skrypnik.financy.repo

import com.skrypnik.financy.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepo : JpaRepository<User, String>