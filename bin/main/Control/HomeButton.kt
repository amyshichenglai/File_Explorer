package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*

class HomeButton(private val model: Model): Button("Home") {

    init {

        onAction = EventHandler {
            model.selectedItem.value = ""
            model.toHome()
        }
    }
}