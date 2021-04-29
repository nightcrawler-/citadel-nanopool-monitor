package com.cafrecode.citadel.vo.responses.core

data class GenericResponse(val status: Boolean, val data: String) {
}

// Extendtion functions for Generic Response. Is this the right place? Seems like it. 'data' can be oe of the below mentioned depending on the endpoint hit
//TODO: Revise, for locale currency too
fun GenericResponse.balance(): String {
    return String.format("%.6f XMR", this.data.toFloat())
}

fun GenericResponse.hashrate(): String {
    return String.format("%.1f H/s", this.data.toFloat())
}