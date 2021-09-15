package com.ilyabuhlakou.mod.specdist.view

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.Stylesheet.Companion.form
import tornadofx.View
import tornadofx.asObservable
import tornadofx.barchart
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.series
import tornadofx.textfield
import tornadofx.vbox

const val INTERVALS_AMOUNT = 20
const val SEED: Double = 36573.1234
const val MULTIPLIER: Double = 43565.0
const val DIVIDER: Double = 65501.0
const val SEQUENCE_SIZE: Int = 150_000

class MainView : View() {

    val seedInput = SimpleDoubleProperty(SEED)
    val aInput = SimpleDoubleProperty(MULTIPLIER)
    val mInput = SimpleDoubleProperty(DIVIDER)
    val sequenceSizeInput = SimpleIntegerProperty(SEQUENCE_SIZE)

    val chartData = mutableListOf<XYChart.Data<String, Number>>().asObservable()

    override val root = vbox {
        hbox {
            form {
                fieldset {
                    field("Seed") {
                        textfield(seedInput) {
                            filterInput { it.controlNewText.isDouble() }
                        }
                    }
                }
            }
            barchart("Гистограмма", CategoryAxis(), NumberAxis()) { series("Numbers", chartData) }.apply {
                setMinSize(900.0, 400.0)
            }
        }
    }
}
