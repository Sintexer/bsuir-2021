package com.ilyabuhlakou.mod.rand.random

import kotlin.math.PI
import kotlin.math.pow

fun rand(seed: Double, a: Double, m: Double): Double {
    return (seed * a) % m
}

fun checkUniformity(sequence: List<Double>): Int {
    return sequence.asSequence()
        .chunked(2)
        .filter { it.size > 1 }
        .map { it[0].pow(2) + it[1].pow(2) }
        .filter { it < 1 }
        .count()
}
