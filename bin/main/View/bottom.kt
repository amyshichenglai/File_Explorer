package ui.javafx.mvc.propertiesmvcextended.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.Label
import ui.javafx.mvc.propertiesmvcextended.model.*

/**
 * DoubleLabel displays the value of the [Model][ui.lectures.javafx.mvc.propertiesmvcextended.model.Model] on a label.
 */
class Bottom(private val model: Model) : Label(), InvalidationListener {

    init {
        model.FileList.addListener(this) // listen to the Property
        model.selectedItem.addListener(this) // listen to the Property
        invalidated(null) // call to set initial text
    }

    override fun invalidated(observable: Observable?) {
        text = "${model.currDir}/${model.selectedItem.value ?: ""}"
    }
}