package com.example.dictionary;

import com.example.dictionary.model.Word;
import com.example.dictionary.utils.SuggestionProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class HelloController implements Initializable {
    @FXML
    public Pane panelContent;
    public Menu showAbout;
    private DictionaryManagement dictionaryManagement;
    private SuggestionProvider<String> provider;
    @FXML
    private TextField wordInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dictionaryManagement = new DictionaryManagement();
        this.dictionaryManagement.insertFromFile();
        provider = SuggestionProvider.create(dictionaryManagement.getDictionary().getWords()
                .stream().map(Word::getWordTarget)
                .collect(Collectors.toList()));
        TextFields.bindAutoCompletion(wordInput, provider);
    }


    public void onClick(MouseEvent mouseEvent) throws IOException {
        if(Objects.equals(wordInput.getText(), "")) System.out.println("Không thể bỏ trống trường tìm kiếm!");
        else {
            Word word = dictionaryManagement.findWord(wordInput.getText());
            if(word == null) System.out.println("Từ không tồn tại!");
            else {
                if(panelContent.getChildren().size() == 0) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Pane newLoadedPane = loader.load();
                    ContentController contentController = loader.getController();
                    contentController.init(word,this);
                    panelContent.getChildren().add(newLoadedPane);
                }
                else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Pane newLoadedPane = loader.load();
                    ContentController contentController = loader.getController();
                    contentController.init(word,this);
                    panelContent.getChildren().set(0, newLoadedPane);
                }
            }
        }
    }

    public void showCreateWord(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InsertWord.fxml"));
            Pane root = fxmlLoader.load();
            CreateWordController createWordController = fxmlLoader.getController();
            createWordController.init(this.dictionaryManagement);
            stage.setScene(new Scene(root));
            stage.setTitle("Create new word");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onChangeInput(KeyEvent keyEvent) {
        provider.clearSuggestions();
        provider.addPossibleSuggestions(dictionaryManagement.getDictionary().getWords()
                .stream().map(Word::getWordTarget)
                .collect(Collectors.toList()));
    }
    public void resetHome() {
        this.panelContent.getChildren().remove(0);
        this.wordInput.setText("");
    }
    public String getWord() {
        return this.wordInput.getText();
    }
    public DictionaryManagement getDictionaryManagement() {
        return this.dictionaryManagement;
    }


    public void showAbout(ActionEvent actionEvent) throws IOException {
        System.out.println("chuong");
        if(panelContent.getChildren().size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
            Pane newLoadedPane = loader.load();
            panelContent.getChildren().add(newLoadedPane);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
            Pane newLoadedPane = loader.load();
            panelContent.getChildren().set(0, newLoadedPane);
        }
    }
}