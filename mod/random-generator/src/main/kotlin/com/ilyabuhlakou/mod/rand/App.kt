package com.ilyabuhlakou.mod.rand

import com.ilyabuhlakou.mod.rand.view.MainView
import tornadofx.App
import tornadofx.launch

class MainApp : App(MainView::class)

fun main(args: Array<String>) {
    launch<MainApp>(args)
}
