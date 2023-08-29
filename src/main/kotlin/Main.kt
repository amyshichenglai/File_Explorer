import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import ui.javafx.mvc.propertiesmvcextended.view.*
import ui.javafx.mvc.propertiesmvcextended.model.*
import ui.javafx.mvc.propertiesmvcextended.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent


class Main : Application() {
    override fun start(primaryStage: Stage?) {
        val myModel = Model()

        // create panels
        val leftPane = ScrollPane(Left(myModel)).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
            vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
            prefWidth = 110.0
            isFitToWidth = true
            isFitToHeight = true
        }

        val topPane = Pane().apply {
            prefHeight = 65.0
        }

        val topPaneContent = VBox(MenuPanel(myModel, primaryStage),
            AnchorPane(
                HomeButton(myModel).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 5.0)
                },
                PrevButton(myModel).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 70.0)
                },
                NextButton(myModel).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 115.0)
                },
                RenameButton(myModel).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 170.0)
                },
                MoveButton(myModel, primaryStage).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 235.0)
                },
                DeleteButton(myModel).apply{
                    AnchorPane.setTopAnchor(this, 5.0)
                    AnchorPane.setLeftAnchor(this, 285.0)
                }).apply{
                prefWidthProperty().bind(topPane.widthProperty())
                prefHeightProperty().bind(topPane.heightProperty())
                background = Background(BackgroundFill(Color.valueOf("#ffffff"), null, null))
            }
        ).apply{
            prefWidthProperty().bind(topPane.widthProperty())
            prefHeightProperty().bind(topPane.heightProperty())
            background = Background(BackgroundFill(Color.valueOf("#00ff00"), null, null))
        }

        topPane.children.add(topPaneContent)

        val centrePane = Center(myModel).apply {
            prefWidth = 100.0
        }

        val bottomPane = Pane(Bottom(myModel)).apply {
            prefHeight = 15.0
            background = Background(BackgroundFill(Color.valueOf("#ffffff"), null, null))
        }


        // put the panels side-by-side in a container
        val root = BorderPane().apply {
            left = leftPane
            center = centrePane
            top = topPane
            bottom = bottomPane
        }

        // create the scene and show the stage
        with (primaryStage!!) {
            scene = Scene(root, 600.0, 450.0)
            title = "A1"
            scene.addEventFilter(KeyEvent.KEY_PRESSED) { event ->
                if (event.code == KeyCode.ENTER) {
                    if (myModel.selectedItem.value.endsWith("/")) {
                        myModel.navigateIn(myModel.selectedItem.value)
                    }
                } else if (event.code == KeyCode.BACK_SPACE) {
                    if (myModel.currDir != myModel.dir) {
                        myModel.navigateOut()
                    }
                }
            }
            show()
        }
    }
}

