package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*
import javafx.scene.control.Alert

class RenameButton(private val model: Model): Button("Rename") {


    init {
        onAction = EventHandler {
            val newFileName = model.promptRename()
            if (newFileName != null && model.rename(newFileName)) {
                val alert = Alert(Alert.AlertType.INFORMATION)
                alert.title = "Success"
                alert.headerText = "Success"
                alert.contentText = "Renamed"
                alert.showAndWait()
            } else {
                val alert = Alert(Alert.AlertType.ERROR)
                alert.title = "Error"
                alert.headerText = "Error"
                alert.contentText = "Failed to rename"
                alert.showAndWait()
            }
        }
    }
}
