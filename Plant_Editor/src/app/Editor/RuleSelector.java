package app.Editor;

import LSystem.LSystemDescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RuleSelector {
    private static ObservableList<String> rules;

    public static void display(LSystemDescription lsd){
        Stage ruleSelector = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 500, 600);
        ruleSelector.initModality(Modality.APPLICATION_MODAL);
        ruleSelector.setTitle("Rule Selection");
        ruleSelector.setResizable(false);

        ObservableList<RuleEntry> entries = FXCollections.observableArrayList();
        TableView<RuleEntry> tableView = new TableView<>();

        TableColumn<RuleEntry, String> rules = new TableColumn<>("Rule");
        rules.setPrefWidth(250);
        rules.setCellValueFactory(new PropertyValueFactory<>("rule"));

        TableColumn<RuleEntry, Float> probs = new TableColumn<>("Probability");
        probs.setPrefWidth(250);
        probs.setCellValueFactory(new PropertyValueFactory<>("prob"));

        tableView.setItems(entries);
        tableView.getColumns().addAll(rules, probs);

        // list view of rules
        for (Character key : lsd.rules.keySet()){
            entries.add(new RuleEntry(key + "=" + lsd.rules.get(key), lsd.probs.get(key)));
        }
        borderPane.setTop(tableView);

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
                entries.add(new RuleEntry(ruleField.getText(), 1.0));
            } catch (Exception exception){
                ErrorScene.display(exception.toString());
            }
            tableView.refresh();
        });

        Button removeButton = new Button();
        removeButton.setText("remove");
        removeButton.setPrefWidth(150);
        removeButton.setOnAction(e -> {
            RuleEntry removed = tableView.getSelectionModel().getSelectedItem();
            entries.remove(removed);
            lsd.rules.remove(removed.getRule().charAt(0));
            lsd.probs.remove(removed.getRule().charAt(0));
            tableView.refresh();
        });

        HBox addRemove = new HBox();
        addRemove.setAlignment(Pos.CENTER);
        addRemove.setSpacing(10);
        addRemove.getChildren().addAll(addButton, ruleField, removeButton);
//        borderPane.setCenter(addRemove);

        // prob
        TextField probField = new TextField("");
        probField.setPrefWidth(150);

        Button probButton = new Button();
        probButton.setText("Set Probability");
        probButton.setPrefWidth(150);
        probButton.setOnAction(e -> {
            try {
                RuleEntry removed = tableView.getSelectionModel().getSelectedItem();
                lsd.probs.remove(removed.getRule().charAt(0));
                lsd.probs.put(removed.getRule().charAt(0), Double.valueOf(probField.getText()));
                removed.prob = Double.valueOf(probField.getText());
                tableView.refresh();

                for (Character c : lsd.probs.keySet()){
                    System.out.println(c + ", " + lsd.probs.get(c));
                }

            } catch (Exception exception){
                ErrorScene.display(probField.getText() + " is not a valid double");
            }
        });

        HBox probWidgets = new HBox();
        probWidgets.setAlignment(Pos.CENTER);
        probWidgets.setSpacing(10);
        probWidgets.getChildren().addAll(probField, probButton);
//        borderPane.setCenter(probWidgets);

        // buttons
        Button okayButton = new Button();
        okayButton.setText("Okay");
        okayButton.setPrefWidth(150);
        okayButton.setOnAction(e -> {
            ruleSelector.close();
        });

        VBox buttonGroups = new VBox();
        buttonGroups.setMinHeight(200);
        buttonGroups.setAlignment(Pos.CENTER);
        buttonGroups.setSpacing(10);
        buttonGroups.getChildren().addAll(addRemove, probWidgets, okayButton);
        borderPane.setBottom(buttonGroups);

//        HBox bottomButtons = new HBox();
//        bottomButtons.setAlignment(Pos.CENTER);
//        bottomButtons.setSpacing(10);
//        bottomButtons.getChildren().addAll(okayButton);
//        borderPane.setBottom(bottomButtons);

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
        lsd.probs.put(key, 1.0);
    }
}
