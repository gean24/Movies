package com.mauro.reto.core.utils

import java.util.*

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
fun String.capitalize(): String = split(" ").joinToString(" ") { it.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
} }
