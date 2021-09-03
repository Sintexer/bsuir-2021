package com.ilyabuhlakou.mod.rand.controller

import com.ilyabuhlakou.mod.rand.random.RandomCollector
import com.ilyabuhlakou.mod.rand.random.RandomGenerator
import tornadofx.Controller

const val V = 61_000

class RandomController(
    var randomCollector: RandomCollector = RandomCollector(RandomGenerator())
) : Controller() {

    fun createNewRandomCollector(seed: Double, a: Double, m: Double) {
        val randomGenerator = RandomGenerator(seed, a, m)
        randomCollector = RandomCollector(randomGenerator)
    }

    fun generateSequence(sequenceSize: Int): List<Double> {
        return randomCollector.generateRandomSequence(sequenceSize)
    }
}
