package com.skrypnik.financy.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
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

@Entity
@Table
data class Income(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "username", nullable = false)
        var user: User? = null,
        @Column(nullable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var creationDate: LocalDateTime? = null,
        @Column(nullable = false)
        var sum: Int,
        @Column(nullable = true)
        var comment: String?,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var category: IncomeCategoryEnum
)