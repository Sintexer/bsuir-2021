package com.ilyabuhlakou.mod.specdist.distribution

import com.ilyabuhlakou.mod.specdist.random.randomList
import kotlin.math.ln
import kotlin.math.sqrt

fun uniformDistribution(leftBorder: Double, rightBorder: Double, size: Int): List<Double> {
    require(size > 0 && leftBorder < rightBorder)
    return randomList(size).map { it * (leftBorder + rightBorder - leftBorder) }
}

fun gaussDistribution(mean: Double, std: Double, size: Int, n: Double = 6.0): List<Double> {
    require(size > 0)
    return (0..size).map { mean + std * sqrt(12 / n) * (randomList(n.toInt()).sum() - n / 2) }
}

fun exponentialDistribution(lambda: Double, size: Int): List<Double> {
    return randomList(size).map { -1 * lambda * ln(it) }
}

fun gammaDistribution(eta: Int, lambda: Double, size: Int): List<Double> {
    return (0..size).map { -1 / lambda * ln(randomList(eta).reduce { acc, i -> acc * i }) }
}

fun minTriangleDistribution(leftBorder: Double, rightBorder: Double, size: Int): List<Double> {
    return (0..size).map { leftBorder + (rightBorder - leftBorder) * (randomList(2).minOrNull() ?: 0.0) }
}

fun maxTriangleDistribution(leftBorder: Double, rightBorder: Double, size: Int): List<Double> {
    return (0..size).map { leftBorder + (rightBorder - leftBorder) * (randomList(2).maxOrNull() ?: 0.0) }
}

fun simpsonDistribution(leftBorder: Double, rightBorder: Double, size: Int): List<Double> {
    return uniformDistribution(leftBorder / 2, rightBorder / 2, size)
        .zip(uniformDistribution(leftBorder / 2, rightBorder / 2, size))
        .map { it.first + it.second }
}
