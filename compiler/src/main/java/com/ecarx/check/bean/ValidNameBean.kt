package com.ecarx.check.bean

class ValidNameBean(
    val name: String,
    val message: String,
    private val severity: String? = "error"
) {
    val checkSeverity: Severity
        get() = when (severity) {
            "info" -> Severity.INFO
            "warn" -> Severity.WARN
            "error" -> Severity.ERROR
            else -> Severity.ERROR
        }
}