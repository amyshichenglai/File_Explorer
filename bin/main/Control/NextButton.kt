package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*

class NextButton(private val model: Model): Button("Next") {

    init {
        onAction = EventHandler {
            if (model.selectedItem.value.endsWith("/")) {
                model.navigateIn(model.selectedItem.value)
            }
        }
    }
}