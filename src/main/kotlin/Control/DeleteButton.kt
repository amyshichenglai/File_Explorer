package ui.javafx.mvc.propertiesmvcextended.control

import javafx.event.EventHandler
import javafx.scene.control.Button
import ui.javafx.mvc.propertiesmvcextended.model.*
import javafx.scene.control.Alert
import java.io.File
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType

class DeleteButton(private val model: Model): Button("Delete") {

    init {
        onAction = EventHandler {
            val fileToDelete = File(model.currDir, model.selectedItem.value)
            val confirmationDialog = Alert(AlertType.CONFIRMATION)
            confirmationDialog.title = "Confirm Delete"
            confirmationDialog.headerText = "Delete Item"
            confirmationDialog.contentText = "Are you sure you want to delete '${model.selectedItem.value}'?"
            val result = confirmationDialog.showAndWait()
            if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if (fileToDelete.delete()) {
                    val alert = Alert(AlertType.INFORMATION)
                    alert.title = "Success"
                    alert.headerText = "Success"
                    alert.contentText = "Deleted successfully"
                    alert.showAndWait()
                    model.updateFileList()
                } else {
                    val alert = Alert(AlertType.ERROR)
                    alert.title = "Error"
                    alert.headerText = "Error"
                    alert.contentText = "Failed to delete"
                    alert.showAndWait()
                }
            }
        }
    }
}