package com.ilyabuhlakou.mod.rand.style

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class BarChartStyle : Stylesheet() {

    companion object {
        val backColor = c(62, 230, 112)
        val chartBar by cssclass()
    }

    init {
        chartBar {
            barFill = backColor
        }
    }
}