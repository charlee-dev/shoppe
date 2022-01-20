package com.adwi.shoppe.utils

expect object Formatter {
    fun numberToCommasString(number: Long?) : String
    fun utsDateToString(uts: Long?, pattern: String) : String
}