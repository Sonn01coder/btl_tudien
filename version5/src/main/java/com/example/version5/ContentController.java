package com.example.version5;

import com.example.dictionary.model.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    @FXML
    public Text wordText;
    @FXML
    public Text pronounceText;
    @FXML
    public Text exampleText;
    @FXML
    public Text spellingText;
    @FXML
    public Label explainText;
    private DictionaryManagement dictionaryManagement;
    private HelloController helloController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello");
    }

    public void init(Word word, HelloController helloController) {
        wordText.setText(word.getWordTarget());
        pronounceText.setText(word.getSpelling());
        exampleText.setText(word.getWordExplain());
        spellingText.setText(word.getSpelling());
        explainText.setText(word.getWordExplain());
        this.dictionaryManagement = helloController.getDictionaryManagement();
        this.helloController = helloController;
    }

    public void playAudioUS(MouseEvent mouseEvent) {
        runAudio(this.wordText.getText() + "_us.mp3");
    }
    public void playAudioUK(MouseEvent mouseEvent) {
        this.runAudio(this.wordText.getText() + "_gb.mp3");
    }
    public void runAudio(String fileName) {
        URL resource = this.getClass().getResource("/audio/" + fileName.substring(0, 2) + "/" + fileName);
        try {
            Media hit = new Media(new File(resource.toURI()).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteWord(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Do you want to delete the word in the dictionary?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== buttonTypeYes) {
            if(!dictionaryManagement.removeWordFromFile(wordText.getText())) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setHeaderText("Delete word failed, please try again latter!");
                alertError.show();
            }
            else {
                Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                alertSuccess.setHeaderText("Delete word successfully!");
                alertSuccess.showAndWait();
                this.helloController.resetHome();
            }
        }
    }

    public void onEditWord(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateWord.fxml"));
            Pane root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Create new word");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
