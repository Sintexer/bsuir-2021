package com.ilyabuhlakou.mod.specdist.view

import com.ilyabuhlakou.mod.specdist.distribution.DEFAULT_SIZE
import com.ilyabuhlakou.mod.specdist.distribution.gaussDistribution
import com.ilyabuhlakou.mod.specdist.histogram.createHistogramColumns
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.View
import tornadofx.action
import tornadofx.asObservable
import tornadofx.barchart
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.series
import tornadofx.textfield
import tornadofx.vbox

const val MEAN = 5.0
const val STD = 5.0
const val N = 5

class GaussView : View() {
    val mean = SimpleDoubleProperty(MEAN)
    val std = SimpleDoubleProperty(STD)
    val n = SimpleIntegerProperty(N)
    val size = SimpleIntegerProperty(DEFAULT_SIZE)

    val chartData = mutableListOf<XYChart.Data<String, Number>>().asObservable()

    override val root = vbox {
        hbox {
            vbox(alignment = Pos.CENTER_LEFT, spacing = 78) {
                form {
                    fieldset {
                        field("Mean") {
                            textfield(mean) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                        field("Std") {
                            textfield(std) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                        field("N") {
                            textfield(n) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                        field("Size") {
                            textfield(size) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                buttonbar {
                    button("Получить распределение").apply{minHeight = 60.0}.action {
                        buildDistributionChart()
                    }
                }
            }
            barchart("Распределение", CategoryAxis(), NumberAxis()) { series("Numbers", chartData) }.apply {
                setMinSize(900.0, 400.0)
                animated = false
            }
        }
    }

    init {
        buildDistributionChart()
    }

    private fun buildDistributionChart() {
        val sequence = gaussDistribution(mean.value, std.value, size.value, n.value.toDouble())
        chartData.setAll(
            createHistogramColumns(INTERVALS_AMOUNT, sequence).map {
                XYChart.Data<String, Number>(it.key.toString(), it.value)
            }.asObservable()
        )
    }
}
