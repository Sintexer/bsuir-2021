package com.ilyabuhlakou.mod.rand

fun createHistogramColumns(intervalsAmount: Int, sequence: List<Double>): List<Map.Entry<Int, Int>> {
    val max = sequence.maxOrNull()!!
    val min = sequence.minOrNull()!!
    val diap = max - min
    val colSize = diap / intervalsAmount
    return sequence.map { (it*intervalsAmount).toInt() }
        .groupingBy { it }
        .eachCount()
        .entries.sortedBy { it.key }
}
