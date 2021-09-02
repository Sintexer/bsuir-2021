package com.ilyabuhlakou.mod.rand.random

class RandomCollector(
    var randomGenerator: RandomGenerator
) {

    fun generateRandomSequence(sequenceSize: Int): List<Double> {
        return (1..sequenceSize).map {
            randomGenerator.nextDouble()
        }.toList()
    }
}
