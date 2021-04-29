package com.cafrecode.citadel.vo.responses.core

import org.junit.Assert.assertEquals
import org.junit.Test

class GenericResponseKtTest {

    val target = GenericResponse(true, "0.005566789870")

    @Test
    fun balance() {
        assertEquals("0.005567 XMR", target.balance())
    }

    @Test
    fun hashrate() {
        assertEquals("0.005567 H/s", target.hashrate())
    }
}