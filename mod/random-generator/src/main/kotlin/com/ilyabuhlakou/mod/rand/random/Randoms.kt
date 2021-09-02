package com.ilyabuhlakou.mod.rand.random

import kotlin.math.PI

fun rand(seed: Double, a: Double, m: Double): Double {
    return (seed * a) % m
}

fun checkUniformity(sequence: List<Double>): Int {
    return sequence.chunked(2)
        .map { it[0].times(2) + it[1].times(2) }
        .filter { it < PI / 4 }
        .count()
}
