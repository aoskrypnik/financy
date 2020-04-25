package com.skrypnik.financy.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.skrypnik.financy.domain.enums.ExpenseCategoryEnum
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Digits

@Entity
@Table
data class Expense(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "username", nullable = false)
        var user: User? = null,
        @Column(nullable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var creationDate: LocalDate,
        @Digits(integer = 10, fraction = 2)
        @Column(nullable = false)
        var sum: BigDecimal,
        @Column(nullable = true)
        var comment: String?,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var category: ExpenseCategoryEnum
)