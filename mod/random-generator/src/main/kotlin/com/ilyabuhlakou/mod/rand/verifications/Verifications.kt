package com.ilyabuhlakou.mod.rand.verifications

fun findAperiodicBorders(sequence: List<Double>, V: Double): Int {
    var i1 = 0
    var i2 = 0
    var found = false
    for (i in sequence.indices) {
        if (sequence[i] == V) {
            found = true
            i1 = i
            break
        }
    }
    if (!found) {
        throw NoAperiodicBordersException()
    }
    found = false
    for (i in (i1 + 1) until sequence.size) {
        if (sequence[i] == V) {
            found = true
            i2 = i
            break
        }
    }
    if (!found) {
        throw NoAperiodicBordersException()
    }
    return i2 - i1
}

fun findPeriod(sequence: List<Double>, v: Int): Int {
    val periodIndices = mutableListOf<Int>()
    for (i in (sequence.size - 1) downTo 0) {
        if (sequence[v] == sequence[i]) {
            periodIndices += i
        } else if (periodIndices.size == 2) {
            break
        }
    }
    if (periodIndices.size < 2) {
        return 0
    }
    return periodIndices[0] - periodIndices[1]
}

fun findAperiodicLength(sequence: List<Double>, period: Int): Int {
    for (i in 0 until (sequence.size - period)) {
        if (sequence[i] == sequence[i + period]) {
            return i + period
        }
    }
    return 0
}
