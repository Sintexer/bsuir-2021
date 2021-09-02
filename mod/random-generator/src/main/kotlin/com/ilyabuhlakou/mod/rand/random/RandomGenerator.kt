package com.ilyabuhlakou.mod.rand.random

class RandomGenerator(
    val seed: Double = 36573.1234,
    val multiplier: Double = 43565.0,
    val divider: Double = 65501.0
) {

    var prev: Double = seed

    fun nextDouble(): Double {
        prev = rand(prev, multiplier, divider)
        return prev / divider
    }
}
