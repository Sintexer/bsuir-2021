package com.ilyabuhlakou.mod.specdist

import com.ilyabuhlakou.mod.specdist.distribution.gaussDistribution
import com.ilyabuhlakou.mod.specdist.random.randomList
import com.ilyabuhlakou.mod.specdist.view.GaussView
import com.ilyabuhlakou.mod.specdist.view.MainView
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.variance
import tornadofx.App
import tornadofx.launch

class MainApp : App(GaussView::class)

fun main(args: Array<String>) {
    launch<MainApp>(args)
//    val randoms = gaussDistribution(5, 5, 5000)
//    println(randoms)
//    println(randoms.median())
//    println(randoms.variance())
}
