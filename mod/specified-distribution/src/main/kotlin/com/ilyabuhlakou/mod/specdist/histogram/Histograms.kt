package com.ilyabuhlakou.mod.specdist.histogram

fun createHistogramColumns(intervalsAmount: Int, sequence: List<Double>): List<Map.Entry<Int, Int>> {
    return sequence.map { (it * intervalsAmount).toInt() }
        .groupingBy { it }
        .eachCount()
        .entries.sortedBy { it.key }
}
