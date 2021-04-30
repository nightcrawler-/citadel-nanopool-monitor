package com.cafrecode.citadel.utils

import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Created by Frederick on 1/8/2016.
 */
object CurrencyFormatUtil {
    private const val PATTERN = "KSh ###,###"
    private const val PATTERN_MINUS_PREFIX = "###,###"
    const val KES_WHOLE = "KSh ###,###"
    const val USD_WHOLE = "$###,###"
    const val USD_DEC = "$###,###.00"
    const val KES_DEC = "Ksh ###,###.00"
    const val GENERIC_DEC = "###,###.00"
    const val GENERIC_WHOLE = "###,###"

    fun format(value: Double): String {
        return DecimalFormat(PATTERN).format(value)
    }

    fun formatSansPrefix(value: BigDecimal?): String {
        return DecimalFormat(PATTERN_MINUS_PREFIX).format(value)
    }

    fun format(value: Double, pattern: String?): String {
        return DecimalFormat(pattern).format(value)
    }
}