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

public class UpdateWordController implements Initializable {
    public TextField wordTargetField;
    public TextField wordExplainField;
    public CheckBox checkbox1;
    public CheckBox checkbox2;
    public CheckBox checkbox3;
    public CheckBox checkbox4;
    public CheckBox checkbox5;
    public CheckBox checkbox6;
    public CheckBox checkbox7;
    public CheckBox checkbox8;
    public CheckBox checkbox9;
    public TextArea exampleField;
    public TextField spellingField;
    private String text;
    private DictionaryManagement dictionaryManagement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void init(Word word, DictionaryManagement dictionaryManagement) {
        this.text = word.getWordTarget();
        this.dictionaryManagement = dictionaryManagement;
        String [] wordforms = word.getWordForm().split(", ");
        for(int i = 0; i < wordforms.length; i++) {
            switch (wordforms[i]) {
                case "Noun": {
                    this.checkbox1.setSelected(true);
                    break;
                }
                case "Adjective": {
                    this.checkbox2.setSelected(true);
                    break;
                }
                case "Interjection": {
                    this.checkbox3.setSelected(true);
                    break;
                }
                case "Adve": {
                    this.checkbox4.setSelected(true);
                    break;
                }
                case "Verd": {
                    this.checkbox5.setSelected(true);
                    break;
                }
                case "Pronoun": {
                    this.checkbox6.setSelected(true);
                    break;
                }
                case "Determiner": {
                    this.checkbox7.setSelected(true);
                    break;
                }
                case "Preposition": {
                    this.checkbox8.setSelected(true);
                    break;
                }
                case "Conjunction": {
                    this.checkbox9.setSelected(true);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        this.exampleField.setText(word.getExample());
        this.spellingField.setText(word.getWordExplain());
        this.wordExplainField.setText(word.getWordExplain());
        this.wordTargetField.setText(word.getWordTarget());
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
        alert.setHeaderText("Do you want to update the word no in the dictionary?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== buttonTypeYes) {
            if(!dictionaryManagement.updateWordFromFile(this.text, word)) {
                alertError.setHeaderText("Update word failed, please try again latter!");
                alertError.show();
            }
            else {
                Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                alertSuccess.setHeaderText("Update word successfully!");
                alertSuccess.show();
            }
        }
    }
}
