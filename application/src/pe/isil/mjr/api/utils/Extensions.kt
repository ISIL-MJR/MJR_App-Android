package pe.isil.mjr.api.utils

fun String.isAlphaNumeric() = matches("[a-zA-Z0-9]+".toRegex())