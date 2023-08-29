package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*

class PrevButton(private val model: Model): Button("Prev") {

    init {
        onAction = EventHandler {
            if (model.currDir != model.dir) {
                model.navigateOut()
            }
        }
    }
}