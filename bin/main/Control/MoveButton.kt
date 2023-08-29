package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*
import javafx.scene.control.Alert
import javafx.stage.DirectoryChooser
import javafx.stage.Stage

class MoveButton(private val model: Model, private val primaryStage: Stage?) : Button("Move") {
    init {
        onAction = EventHandler {
            try {
                val directoryChooser = DirectoryChooser()
                directoryChooser.title = "Move Item"
                val selectedDirectory = directoryChooser.showDialog(primaryStage)
                if (selectedDirectory != null && model.move(selectedDirectory.absolutePath)) {
                    val alert = Alert(Alert.AlertType.INFORMATION)
                    alert.title = "Success"
                    alert.headerText = "Success"
                    alert.contentText = "Moved to ${selectedDirectory.absolutePath}"
                    alert.showAndWait()
                } else {
                    throw Exception("Failed to move")
                }
            } catch (e: Exception) {
                val alert = Alert(Alert.AlertType.ERROR)
                alert.title = "Error"
                alert.headerText = "Error"
                alert.contentText = "Failed to move"
                alert.showAndWait()
            }
        }
    }
}