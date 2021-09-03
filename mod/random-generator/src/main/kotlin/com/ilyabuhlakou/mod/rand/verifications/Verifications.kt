package com.ilyabuhlakou.mod.rand.verifications

fun calculateAperiodicLength(sequence: List<Double>, firstCheckedIndex: Int): Int {
    val list: List<Double> = ArrayList(sequence)
    val V = list[firstCheckedIndex]
    val P = findAperiodicBorders(sequence, V)
    return findAperiodicLength(sequence, P)
}

fun findAperiodicLength(sequence: List<Double>, P: Int): Int {
    for (i in 0 until sequence.size - P) {
        if (sequence[i] == sequence[i + P]) {
//            println("Найдена пара равных x1=${sequence[i]}, x2=${sequence[i + P]}, i1=$i, i2=${i + P})")
//            println(sequence.subList(maxOf(i - 5, 0), minOf(i + P + 5, sequence.size - 1)))
            return i + P
        }
    }
    return 0
}

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
