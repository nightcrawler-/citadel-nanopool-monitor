package com.cafrecode.citadel.vo.responses.core

import com.cafrecode.citadel.utils.CurrencyFormatUtil
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

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
    @SerializedName("unconfirmed_balance")
    val unconfirmedBalance: Double,
    val hashrate: Double,
    //Include the other fields anyways, just so to have one main response data struct
    val priceBtc: Double,
    val priceUsd: Double,
    val priceEur: Double,
    val priceCny: Double,
    val priceGbp: Double
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
fun Double.currencyFormat(): String {
    return String.format("%.6f XMR", this)
}

fun Double.hashrateFormat(): String {
    // Clearly not the best of names, refine
    return CurrencyFormatUtil.formatSansPrefix(BigDecimal(this)) + " H/s"
}