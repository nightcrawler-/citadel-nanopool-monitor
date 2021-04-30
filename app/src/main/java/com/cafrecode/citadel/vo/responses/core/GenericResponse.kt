package com.cafrecode.citadel.vo.responses.core

import com.cafrecode.citadel.utils.CurrencyFormatUtil
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import kotlin.math.round

data class GenericResponse(val status: Boolean, val data: String)

data class GeneralResponse(val status: Boolean, val data: GeneralData)

/*
        "price_btc": 0.00735837,
        "price_usd": 414.9,
        "price_rur": 31177,
        "price_eur": 344.9,
        "price_cny": 2686.75,
        "price_gbp": 300.34
 */
data class GeneralData(
    val balance: Double,
    val hashrate: Double,
    @SerializedName("unconfirmed_balance") val unconfirmedBalance: Double,
    //Include the other fields anyways, just so to have one main response data struct
    @SerializedName("price_btc") val priceBtc: Double,
    @SerializedName("price_usd") val priceUsd: Double,
    @SerializedName("price_eur") val priceEur: Double,
    @SerializedName("price_cny") val priceCny: Double,
    @SerializedName("price_gbp") val priceGbp: Double,

    val payout: Double
)

// Extension functions for Generic Response. Is this the right place? Seems like it. 'data' can be oe of the below mentioned depending on the endpoint hit
//TODO: Revise, for locale currency too
fun GenericResponse.balance(): String {
    return String.format("%.6f XMR", this.data.toFloat())
}

fun GenericResponse.hashrate(): String {
    return String.format("%.1f H/s", this.data.toFloat())
}

//TODO: Use locale or preset currency
fun Double.currencyFormat(symbol: String): String {
    return String.format("%.6f %s", this, symbol)
}

/**
 * 2significant digits
 */
fun Double.currencyFormatShort(symbol: String): String {
    return String.format("%.2f %s", this, symbol)
}

fun Double.hashrateFormat(): String {
    // Clearly not the best of names, refine
    return CurrencyFormatUtil.formatSansPrefix(BigDecimal(this)) + " H/s"
}

fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}