package ui.javafx.mvc.propertiesmvcextended.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Pane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import ui.javafx.mvc.propertiesmvcextended.model.*
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import java.io.File

class Center(private val model: Model) : Pane(), InvalidationListener {

    init {
        model.selectedItem.addListener(this) // listen to the Property
        invalidated(null) // call to set initial text
    }

    override fun invalidated(observable: Observable?) {
        children.clear()
        val contentpath = File(model.currDir, model.selectedItem.value)
        if (contentpath.canRead()) {
            if (model.selectedItem.value.endsWith("png") || model.selectedItem.value.endsWith("jpg") || model.selectedItem.value.endsWith(
                    "bmp")
            ) {
                val imagePath = contentpath.toURI().toString()
                val imageView = ImageView(Image(imagePath)).apply {
                    fitWidthProperty().bind(this@Center.widthProperty())
                    fitHeightProperty().bind(this@Center.heightProperty())
                    isPreserveRatio = true
                }
                this.children.add(imageView)
            } else if (model.selectedItem.value.endsWith("txt") || model.selectedItem.value.endsWith("md")) {
                val content = Label(contentpath.readText()).apply {
                    isWrapText = true
                    prefWidthProperty().bind(this@Center.widthProperty())
                }
                val textContent = ScrollPane(content).apply {
                    fitToWidthProperty().set(true)
                    vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
                    hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                    prefWidthProperty().bind(this@Center.widthProperty())
                    prefHeightProperty().bind(this@Center.heightProperty())
                }
                children.setAll(textContent)
            } else if (model.selectedItem.value.endsWith("/")) {
                children.clear()
            } else if (model.selectedItem.value != ""){
                val unsupported = Label("Unsupported type").apply {
                    alignment = Pos.CENTER
                }
                val unsupportedbox = VBox(unsupported).apply{
                    alignment = Pos.CENTER
                    prefWidthProperty().bind(this@Center.widthProperty())
                    prefHeightProperty().bind(this@Center.heightProperty())
                }
                children.add(unsupportedbox)
            }
        } else {
            val unreadable = Label("File cannot be read").apply{
                alignment = Pos.CENTER
            }
            val unreadablebox = VBox(unreadable).apply{
                alignment = Pos.CENTER
                prefWidthProperty().bind(this@Center.widthProperty())
                prefHeightProperty().bind(this@Center.heightProperty())
            }
            children.add(unreadablebox)
        }
    }
}
