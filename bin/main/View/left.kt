package ui.javafx.mvc.propertiesmvcextended.view


import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.scene.control.ListView
import ui.javafx.mvc.propertiesmvcextended.model.*

class Left(private val model: Model) : ListView<String>(), InvalidationListener {

    init {
        model.FileList.addListener(this) // listen to the Property
        selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (model.FileList.isNotEmpty()) {
                model.selectedItem.value = newValue
            }
        }

        onMouseClicked = EventHandler { event ->
            if (event.clickCount == 2 && model.selectedItem.value.endsWith("/")) {
                model.navigateIn(model.selectedItem.value)
            }
        }

        invalidated(null) // call to set initial text
    }

    override fun invalidated(observable: Observable?) {
        items = model.FileList
    }
}
