package com.example.dictionary;

import com.example.dictionary.model.Word;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateWordController implements Initializable {
    private DictionaryManagement dictionaryManagement;
    public TextField wordTargetField;
    public TextField wordExplainField;
    public TextField spellingField;
    public CheckBox checkbox1;
    public CheckBox checkbox2;
    public CheckBox checkbox3;
    public CheckBox checkbox5;
    public CheckBox checkbox4;
    public CheckBox checkbox6;
    public CheckBox checkbox7;
    public CheckBox checkbox8;
    public CheckBox checkbox9;
    public TextArea exampleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onCloseWindow(MouseEvent mouseEvent)  {
        Node source = (Node)  mouseEvent.getSource();
        String example = Arrays.asList(exampleField.getText().split("\n")).toString();
        Word word = new Word("school", "trường", "test", "test", example);
        dictionaryManagement.insertWordFromFile(word);
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void onSaveWord(MouseEvent mouseEvent) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setHeaderText("Không để trống các trường bắt buộc!");
        if(wordExplainField.getText().isEmpty() || wordTargetField.getText().isEmpty() || spellingField.getText().isEmpty()) {
            alertError.show();
            return;
        }
        StringBuilder wordForm = new StringBuilder("");
        if(checkbox1.isSelected()) wordForm.append(checkbox1.getText()).append(", ");
        if(checkbox2.isSelected()) wordForm.append(checkbox2.getText()).append(", ");
        if(checkbox3.isSelected()) wordForm.append(checkbox3.getText()).append(", ");
        if(checkbox4.isSelected()) wordForm.append(checkbox4.getText()).append(", ");
        if(checkbox5.isSelected()) wordForm.append(checkbox5.getText()).append(", ");
        if(checkbox6.isSelected()) wordForm.append(checkbox6.getText()).append(", ");
        if(checkbox7.isSelected()) wordForm.append(checkbox7.getText()).append(", ");
        if(checkbox8.isSelected()) wordForm.append(checkbox8.getText()).append(", ");
        if(checkbox9.isSelected()) wordForm.append(checkbox9.getText()).append(", ");
        if(wordForm.length() == 0) {
            alertError.show();
            return;
        }
        wordForm.delete(wordForm.length() - 2, wordForm.length());
        String example = Arrays.asList(exampleField.getText().split("\n")).toString();
        Word word = new Word(wordTargetField.getText(), wordExplainField.getText(), wordForm.toString(), spellingField.getText(), example);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Do you want to save the word no in the dictionary?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== buttonTypeYes) {
            if(!dictionaryManagement.insertWordFromFile(word)) {
                alertError.setHeaderText("Create new words failed, please try again latter!");
                alertError.show();
            }
            else {
                Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                alertSuccess.setHeaderText("Create new words successfully!");
                alertSuccess.show();
                this.reset();
            }
        }
    }
    public void init(DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
    }
    private void reset() {
        this.checkbox1.setSelected(false);
        this.checkbox2.setSelected(false);
        this.checkbox3.setSelected(false);
        this.checkbox4.setSelected(false);
        this.checkbox5.setSelected(false);
        this.checkbox6.setSelected(false);
        this.checkbox7.setSelected(false);
        this.checkbox8.setSelected(false);
        this.checkbox9.setSelected(false);
        this.exampleField.setText("");
        this.spellingField.setText("");
        this.wordTargetField.setText("");
        this.wordExplainField.setText("");
    }
}
