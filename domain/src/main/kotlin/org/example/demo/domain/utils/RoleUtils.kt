package org.example.demo.domain.utils

import org.example.demo.domain.models.UserRole

fun String.parseRoleSet() =
    this
        .split(',')
        .map { UserRole.valueOf(it.uppercase()) }
        .toSet()


fun Set<UserRole>.toRoleString() = this.joinToString(",")