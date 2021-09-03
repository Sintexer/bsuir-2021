package com.ilyabuhlakou.mod.rand.style

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class BarChartStyle : Stylesheet() {

    companion object {
        val backColor = c(27, 181, 78)
        val chartBar by cssclass()
    }

    init {
        chartBar {
            barFill = backColor
        }
    }
}