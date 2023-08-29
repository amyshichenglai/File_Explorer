package ui.javafx.mvc.propertiesmvcextended.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.*
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import ui.javafx.mvc.propertiesmvcextended.model.*
import java.io.File

class MenuPanel(private val model: Model, private val primaryStage: Stage?) : MenuBar(), InvalidationListener {

    init {
        model.FileList.addListener(this) // listen to the Property
        invalidated(null) // call to set initial text
    }

    override fun invalidated(observable: Observable?) {
        menus.clear()
        menus.addAll(
            Menu("File").apply {
                model.FileList.forEach { file ->
                    val menuItem = MenuItem(file)
                    menuItem.setOnAction {
                        model.selectedItem.value = file
                    }
                    items.add(menuItem)
                }
            },
            Menu("View").apply {
                val homeAction = MenuItem("Home")
                homeAction.setOnAction {
                    model.selectedItem.value = ""
                    model.toHome()
                }
                items.addAll(homeAction)
            },
            Menu("Actions").apply {
                val renameAction = MenuItem("Rename")
                renameAction.setOnAction {
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
                val moveAction = MenuItem("Move")
                moveAction.setOnAction {
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
                val deleteAction = MenuItem("Delete")
                deleteAction.setOnAction {
                    val fileToDelete = File(model.currDir, model.selectedItem.value)

                    val confirmationDialog = Alert(Alert.AlertType.CONFIRMATION)
                    confirmationDialog.title = "Confirm Delete"
                    confirmationDialog.headerText = "Delete File"
                    confirmationDialog.contentText = "Are you sure you want to delete '${model.selectedItem.value}'?"
                    val result = confirmationDialog.showAndWait()
                    if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                        if (fileToDelete.delete()) {
                            val successAlert = Alert(Alert.AlertType.INFORMATION)
                            successAlert.title = "Success"
                            successAlert.headerText = "Success"
                            successAlert.contentText = "Deleted successfully"
                            successAlert.showAndWait()
                            model.updateFileList()
                        } else {
                            val errorAlert = Alert(Alert.AlertType.ERROR)
                            errorAlert.title = "Error"
                            errorAlert.headerText = "Error"
                            errorAlert.contentText = "Failed to delete"
                            errorAlert.showAndWait()
                        }
                    }
                }
                items.addAll(renameAction, SeparatorMenuItem(), moveAction, SeparatorMenuItem(), deleteAction)
            },
            Menu("Option").apply {
                val prevAction = MenuItem("Prev")
                prevAction.setOnAction {
                    if (model.currDir != model.dir) {
                        model.navigateOut()
                    }
                }
                val nextAction = MenuItem("Next")
                nextAction.setOnAction {
                    if (model.selectedItem.value.endsWith("/")) {
                        model.navigateIn(model.selectedItem.value)
                    }
                }
                items.addAll(prevAction, SeparatorMenuItem(), nextAction)
            }
        )
    }
}