package com.ilyabuhlakou.mod.specdist.histogram

fun createHistogramColumns(intervalsAmount: Int, sequence: List<Double>): List<Map.Entry<Int, Int>> {
    val max = sequence.maxOrNull() ?: 1.0
    return sequence
        .map { it / max }
        .map { (it * intervalsAmount).toInt() }
        .groupingBy { it }
        .eachCount()
        .entries.sortedBy { it.key }
}
