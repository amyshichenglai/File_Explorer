package ui.javafx.mvc.propertiesmvcextended.model

import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TextInputDialog
import java.io.File

class Model {

    val dir = File("${System.getProperty("user.dir")}/test/")

    var currDir = dir
        set(value) {
            field = value
            updateFileList()
        }

    val fileList: ObservableList<String> = FXCollections.observableArrayList()

    val selectedItem = SimpleStringProperty("")

    val CurrDir: ReadOnlyObjectProperty<File> = ReadOnlyObjectWrapper(currDir).readOnlyProperty
    val FileList: ReadOnlyListProperty<String> = ReadOnlyListWrapper(fileList).readOnlyProperty

    init {
        updateFileList()
    }

    fun updateFileList() {
        fileList.clear()
        currDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                fileList.add("${file.name}/")
            } else {
                fileList.add(file.name)
            }
        }
        fileList.sort()
        selectedItem.value = ""
    }

    fun navigateIn(dirSelected: String) {
        val newDir = File(currDir, dirSelected)
        if (newDir.isDirectory) {
            currDir = newDir
        }
    }

    fun toHome() {
        currDir = dir
    }

    fun navigateOut() {
        currDir = File(currDir.parent)
    }

    fun rename(newName: String): Boolean {
        try {
            val selectedFile = File(currDir, selectedItem.value)
            val renamedFile = File(currDir, newName)
            if ((newName != "") &&
                ((newName.endsWith("/")  && (newName != "*/")) ||
                (newName.endsWith(".txt") && (newName != "*.txt")) ||
                (newName.endsWith(".md") && (newName != "*.md")) ||
                (newName.endsWith(".png") && (newName != "*.png")) ||
                (newName.endsWith(".jpg") && (newName != "*.jpg")) ||
                (newName.endsWith(".bmp") && (newName != "*.bmp")))) {
                val isNameRepeated = fileList.any { it == newName }
                if (!isNameRepeated) {
                    return selectedFile.renameTo(renamedFile)
                }
            }
            return false
        } catch (error: Exception) {
            return false
        } finally {
            updateFileList()
        }
    }

    fun promptRename(): String? {
        val dialog = TextInputDialog(selectedItem.value)
        dialog.title = "Rename Item"
        dialog.headerText = "Enter the new name (directory must end with /, filenames must end with .txt, .md, .png, .jpg, or .bmp)"
        dialog.contentText = "New name:"
        val result = dialog.showAndWait()
        return result.orElse(null)
    }

    fun move(newDir: String): Boolean {
        try {
            val selectedFile = File(currDir, selectedItem.value)
            if (File(newDir).isDirectory && selectedFile.exists()) {
                val targetFile = File(File(newDir), selectedFile.name)
                val isNameRepeated = File(newDir).listFiles().any { it.name == selectedFile.name }
                if (!isNameRepeated) {
                    return selectedFile.renameTo(targetFile)
                }
            }
            return false
        } catch (error: Exception) {
            return false
        } finally {
            updateFileList()
        }
    }
}
