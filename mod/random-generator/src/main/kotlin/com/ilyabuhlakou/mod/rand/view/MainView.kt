package com.ilyabuhlakou.mod.rand.view

import com.ilyabuhlakou.mod.rand.controller.RandomController
import com.ilyabuhlakou.mod.rand.createHistogramColumns
import com.ilyabuhlakou.mod.rand.random.checkUniformity
import com.ilyabuhlakou.mod.rand.verifications.NoAperiodicBordersException
import com.ilyabuhlakou.mod.rand.verifications.calculateAperiodicLength
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.paint.Color
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.nield.kotlinstatistics.variance
import tornadofx.*
import kotlin.math.PI

const val INTERVALS_AMOUNT = 20
const val SEED: Double = 36573.1234
const val MULTIPLIER: Double = 43565.0
const val DIVIDER: Double = 65501.0
const val SEQUENCE_SIZE: Int = 150_000

class MainView : View() {
    val seedInput = SimpleDoubleProperty(SEED).apply {

    }
    val aInput = SimpleDoubleProperty(MULTIPLIER)
    val mInput = SimpleDoubleProperty(DIVIDER)
    val sequenceSizeInput = SimpleIntegerProperty(SEQUENCE_SIZE)

    val mato = SimpleDoubleProperty()
    val disp = SimpleDoubleProperty()
    val sko = SimpleDoubleProperty()

    val kText = SimpleStringProperty()
    val knText = SimpleStringProperty()
    val piText = SimpleStringProperty()
    val length = SimpleStringProperty()

    val controller: RandomController by inject()
    val myData = mutableListOf<XYChart.Data<String, Number>>().asObservable()

    var sequence = listOf<Double>()

    init {
        updateChartInfo()
    }

    override val root = vbox {
        form {
            fieldset(labelPosition = Orientation.VERTICAL) {
                hbox(50, alignment = Pos.CENTER) {
                    vbox(20) {
                        hbox(20) {
                            vbox(20) {
                                field("seed") {
                                    textfield(seedInput) {
                                        filterInput { it.controlNewText.isDouble() }
                                    }
                                }
                                field("a") {
                                    textfield(aInput) {
                                        filterInput { it.controlNewText.isDouble() }
                                    }
                                }
                                field("m") {
                                    textfield(mInput) {
                                        filterInput { it.controlNewText.isDouble() }
                                    }
                                }
                            }
                            vbox(20) {
                                field("Размер последовтельности") {
                                    textfield(sequenceSizeInput) {
                                        filterInput { it.controlNewText.isInt() }
                                    }
                                }
                            }
                            vbox(20) {
                                field("Матожидание") {
                                    textfield(mato){
                                        isEditable = false
                                    }
                                }
                                field("Дисперсия") {
                                    textfield(disp){
                                        isEditable = false
                                    }
                                }
                                field("СКО") {
                                    textfield(sko){
                                        isEditable = false
                                    }
                                }
                            }
                            vbox(20) {
                                field("K") {
                                    textfield(kText){
                                        isEditable = false
                                    }
                                }
                                field("2K/N") {
                                    textfield(knText){
                                        isEditable = false
                                    }
                                }
                                field("PI/4") {
                                    textfield(piText){
                                        isEditable = false
                                    }
                                }
                                field("Расстояние между повторениями") {
                                    textfield(length){
                                        isEditable = false
                                    }
                                }
                            }
                        }
                        hbox(alignment = Pos.CENTER) {
                            button("Обновить") {
                                action {
                                    updateAll()
                                }
                            }.setMinSize(250.0, 50.0)
                        }
                    }
                }
                barchart("Гистограмма", CategoryAxis(), NumberAxis()) { series("Numbers", myData) }.apply {
                    setMinSize(900.0, 400.0)

                }
            }
        }
    }

    fun updateAll() {
        operateData()
        updateChartInfo()
    }

    fun updateChartInfo() {
        generateSequence()
        evaluateStatistic()
        updateChart()
        findValidityInfo()
    }

    fun operateData() {
        val seed = seedInput.get()
        val a = aInput.get()
        val m = mInput.get()
        controller.createNewRandomCollector(seed, a, m)
    }

    fun updateChart() {
        myData.setAll(
            createHistogramColumns(INTERVALS_AMOUNT, sequence).map {
                XYChart.Data<String, Number>(it.key.toString(), it.value)
            }.asObservable()
        )
    }

    fun generateSequence() {
        val sequenceSize = sequenceSizeInput.get()
        this.sequence = controller.generateSequence(sequenceSize)
    }

    fun evaluateStatistic() {
        mato.set(sequence.median())
        disp.set(sequence.variance())
        sko.set(sequence.standardDeviation())
    }

    fun findValidityInfo() {
        val K = checkUniformity(sequence)
        kText.set(K.toString())
        knText.set(((2 * K).toDouble() / sequence.size).toString())
        piText.set((PI / 4).toString())
        length.set(getMaxAperiodicLength())
    }

    fun getMaxAperiodicLength(): String {
        val v = sequence.size - (sequence.size / 20)
        return try {
            val L = calculateAperiodicLength(sequence, v)
            L.toString()
        } catch (e: NoAperiodicBordersException) {
            "не найдено для $v индекса"
        }
    }
}