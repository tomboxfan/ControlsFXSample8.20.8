package org.controlsfx.samples;

import static org.controlsfx.control.decoration.Decorator.addDecoration;
import static org.controlsfx.control.decoration.Decorator.removeAllDecorations;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import org.controlsfx.ControlsFXSample;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.control.decoration.StyleClassDecoration;

public class HelloDecorator extends ControlsFXSample {
    
    private final TextField field = new TextField();
    
    @Override public String getSampleName() {
        return "Decorations";
    }
    
    @Override public String getJavaDocURL() {
        return Utils.JAVADOC_BASE + "org/controlsfx/control/decoration/Decorator.html";
    }
    
    @Override public Node getPanel(final Stage stage) {
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setMaxHeight(Double.MAX_VALUE);
        root.getChildren().addAll(field);
        
        // for the sake of this sample we have to install a custom css file to style the sample
        // but we can't do this until the scene is set on the pane
        root.sceneProperty().addListener(
         o -> {
           if (root.getScene() != null) {
               Platform.runLater(() -> root.getScene().getStylesheets().add(HelloDecorator.class.getResource("decorations.css").toExternalForm()));
           }
         }
        );
        
        return new ScrollPane(root);
    }
    
    @Override
    public Node getControlPanel() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(30, 30, 0, 30));
        
        int row = 0;
        
        // --- show decorations
        Label showDecorationsLabel = new Label("Show decorations: ");
        showDecorationsLabel.getStyleClass().add("property");
        grid.add(showDecorationsLabel, 0, row);
        ChoiceBox<String> decorationTypeBox = new ChoiceBox<>(FXCollections.observableArrayList("None", "Node", "CSS", "Node + CSS", "Image"));
        decorationTypeBox.getSelectionModel().selectedItemProperty().addListener(
        (o, oldItem, newItem) -> {
              removeAllDecorations(field);
              switch (newItem) {
                  case "None":
                      break;
                  case "Node": {
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED), Pos.TOP_LEFT));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED), Pos.TOP_CENTER));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.RED), Pos.TOP_RIGHT));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN), Pos.CENTER_LEFT));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN), Pos.CENTER));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN), Pos.CENTER_RIGHT));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE), Pos.BOTTOM_LEFT));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE), Pos.BOTTOM_CENTER));
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.BLUE), Pos.BOTTOM_RIGHT));
                      break;
                  }
                  case "CSS": {
                      addDecoration(field, new StyleClassDecoration("warning"));
                      break;
                  }
                  case "Node + CSS": {
                      addDecoration(field, new GraphicDecoration(createDecoratorNode(Color.GREEN), Pos.CENTER_RIGHT));
                      addDecoration(field, new StyleClassDecoration("success"));
                      break;
                  }
                  case "Image": {
                      addDecoration(field, new GraphicDecoration(createImageNode(), Pos.BOTTOM_LEFT));
                      break;
                  }
              }
          }
        );
        grid.add(decorationTypeBox, 1, row++);
        
        // --- Toggle text field visibility-------------------------------
        Label showTextFieldLabel = new Label("TextField visible: ");
        showTextFieldLabel.getStyleClass().add("property");
        grid.add(showTextFieldLabel, 0, row);
        ToggleButton fieldVisibleBtn = new ToggleButton("Press");
        fieldVisibleBtn.setSelected(true);
        field.visibleProperty().bindBidirectional(fieldVisibleBtn.selectedProperty());
        grid.add(fieldVisibleBtn, 1, row++);
        //---------------------------------------------------------------
        
        return grid;
    }
    
    private Node createDecoratorNode(Color color) {
    	Rectangle d = new Rectangle(7, 7);
        d.setFill(color);
        return d;
    }
    
    private Node createImageNode() {
        Image image = new Image("/org/controlsfx/samples/security-low.png");
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
}