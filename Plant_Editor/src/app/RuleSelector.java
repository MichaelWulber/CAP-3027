package app;

import LSystem.LSystemDescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RuleSelector {
    private static ObservableList<String> rules;

    public static void display(LSystemDescription lsd){
        Stage ruleSelector = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 500, 500);
        ruleSelector.initModality(Modality.APPLICATION_MODAL);
        ruleSelector.setTitle("Rule Selection");
        ruleSelector.setResizable(false);

        // list view of rules
        rules = FXCollections.observableArrayList();
        for (Character key : lsd.rules.keySet()){
            rules.add(key + "=" + lsd.rules.get(key));
        }
        ListView listView = new ListView(rules);
        borderPane.setTop(listView);

        // add and remove
        TextField ruleField = new TextField("");
        ruleField.setPrefWidth(150);

        Button addButton = new Button();
        addButton.setText("add");
        addButton.setPrefWidth(150);
        addButton.setOnAction(e -> {
            try {
                String rule = ruleField.getText();
                processRule(rule, lsd);
                rules.add(ruleField.getText());
            } catch (Exception exception){
                // error dialogue
            }
        });

        Button removeButton = new Button();
        removeButton.setText("remove");
        removeButton.setPrefWidth(150);
        removeButton.setOnAction(e -> {
            String removed = (String) listView.getSelectionModel().getSelectedItem();
            rules.remove(removed);
            lsd.rules.remove(removed.charAt(0));
        });

        HBox addRemove = new HBox();
        addRemove.setAlignment(Pos.CENTER);
        addRemove.setSpacing(10);
        addRemove.getChildren().addAll(addButton, ruleField, removeButton);
        borderPane.setCenter(addRemove);

        // buttons
        Button okayButton = new Button();
        okayButton.setText("okay");
        okayButton.setPrefWidth(150);
        okayButton.setOnAction(e -> {
            ruleSelector.close();
        });

        HBox bottomButtons = new HBox();
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setSpacing(10);
        bottomButtons.getChildren().addAll(okayButton);
        borderPane.setBottom(bottomButtons);

        ruleSelector.setScene(scene);
        ruleSelector.showAndWait();
    }

    private static void processRule(String rule, LSystemDescription lsd) throws Exception{
        rule = rule.replaceAll("\\s", "");
        char key = rule.charAt(0);
        StringBuilder s;
        if (lsd.rules.containsKey(key)){
            throw new Exception("Specified key already has been defined!");
        }
        if (rule.charAt(1) != '='){
            throw  new Exception("Rule Format Exception!");
        } else {
            s = new StringBuilder(rule.substring(2, rule.length()));
        }
        lsd.addRule(key, s);
    }
}
