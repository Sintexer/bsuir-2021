package com.ilyabuhlakou.mod.rand

import com.ilyabuhlakou.mod.rand.random.RandomCollector
import com.ilyabuhlakou.mod.rand.random.RandomGenerator
import com.ilyabuhlakou.mod.rand.random.checkUniformity
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.nield.kotlinstatistics.variance
import kotlin.math.PI

fun main() {
    do {
        generatorCycle()
        println("1 - еще разок :)")
        println("Anything - exit")
    } while (readLine() == "1")
}

fun generatorCycle() {
    println("1 - Ввести все числа")
    println("2 - Ввести только размер последовательности, а остальное константы")
    println("Enter или что угодно другое - R0, a, m и размер последовательности = константы ")
    val input = readLine()
    val randomGenerator = when (input) {
        "1" -> getRandomGeneratorFromConsole()
        else -> RandomGenerator()
    }
    val sequenceSize = when (input) {
        "1", "2" -> {
            println("Введите размер последовательности:")
            readLine()!!.toInt()
        }
        else -> 70_000
    }
    val randomCollector = RandomCollector(randomGenerator)
    val sequence = randomCollector.generateRandomSequence(sequenceSize)
    println(createHistogramColumns(20, sequence))
    println("Матожидание: ${sequence.median()}")
    println("Дисперсия: ${sequence.variance()}")
    println("СКО: ${sequence.standardDeviation()}")
    val K = checkUniformity(sequence)
    println("K: $K; 2*K/N = ${(2 * K).toDouble() / sequence.size}; PI/4=${PI / 4}")
}

fun getRandomGeneratorFromConsole(): RandomGenerator {
    println("Введите seed (R0): ")
    val seed = readLine()!!.toDouble()
    println("Введите a: ")
    val a = readLine()!!.toDouble()
    println("Введите m: ")
    val m = readLine()!!.toDouble()
    return RandomGenerator(seed, a, m)
}
