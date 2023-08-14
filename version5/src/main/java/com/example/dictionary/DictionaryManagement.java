package com.example.dictionary;

import com.example.dictionary.model.Dictionary;
import com.example.dictionary.model.Word;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DictionaryManagement {
    private final Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập vào số lượng từ: ");
        int totalWords = scanner.nextInt();
        IntStream.range(0, totalWords).forEach(e -> {
            System.out.print("Nhập từ tiếng anh: ");
            String wordTarget = scanner.next();
            System.out.print("Nhập vào giải thích tiếng việt: ");
            String wordExplain = scanner.next();
            Word word = new Word(wordTarget, wordExplain);
            dictionary.getWords().add(word);
        });
    }

    public void insertFromFile() {
        try {
            URL resource = HelloApplication.class.getClassLoader().getResource("dictionary.txt"); // Lấy đường dẫn file trong thư mục resources`
            File file = new File(resource.toURI()); // Tạo đối tượng file dùng để đọc file
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) { // Duyệt từng hàng trong file
                String data = myReader.nextLine();
                String wordTarget = data.split("\t")[0]; // Tách chuỗi data bởi dấu tab thành mảng string gồm 2 phần tử, phân tử 1 là từ, phần tử 2 là nghĩa của từ đó
                String wordExplain = data.split("\t")[1];
                String wordForm = data.split("\t")[2];
                String speeling = data.split("\t")[3];
                String example = convertArrayToString(data.split("\t")[4]);
                Word word = new Word(wordTarget, wordExplain, wordForm, speeling, example);
                dictionary.getWords().add(word); // Thêm từ đọc từ file vào từ điển
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Đọc file không thành công, vui lòng kiểm tra lại!");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println("Đường dẫn file bị lỗi vui lòng kiểm tra lại!");
            throw new RuntimeException(e);
        }
    }

    public void deleteFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ cần xóa: ");
        String word = scanner.next();
        for (Word element : this.dictionary.getWords()) {
            if (element.getWordTarget().equals(word)) {
                this.dictionary.getWords().remove(element);
                return;
            }
        }
        System.out.println("Từ nhập vào không tồn tại vui lòng kiểm tra lại!");
    }

    public void editFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ cần sửa: ");
        String word = scanner.next();
        for (Word element : this.dictionary.getWords()) {
            if (element.getWordTarget().equals(word)) {
                System.out.print("Nhập vào giải thích tiếng việt của từ: ");
                String wordExplain = scanner.next();
                element.setWordTarget(wordExplain);
                return;
            }
        }
        System.out.println("Từ nhập vào không tồn tại vui lòng kiểm tra lại!");
    }

    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ muốn tìm kiếm: ");
        String word = scanner.next();
        for (Word element : this.dictionary.getWords()) {
            if (element.getWordTarget().equals(word)) {
                System.out.format("Kết quả từ cần tìm là:\n%s: %s\n", element.getWordTarget(), element.getWordExplain());
                return;
            }
        }
        System.out.println("Không tìm thấy từ cần tìm!");
    }
    public void dictionaryExportToFile() {
        try {
            FileWriter myWriter = new FileWriter("export_words.txt");
            StringBuilder stringBuilder = new StringBuilder("");
            for(Word word : dictionary.getWords()) {
                stringBuilder.append(word.getWordTarget()).append(": ").append(word.getWordExplain()).append("\n");
            }
            myWriter.write(stringBuilder.toString());
            System.out.println("Xuất file thành công!");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Xuất file không thành công");
            e.printStackTrace();
        }
    }
    public Word findWord(String wordTarget) {
        for(Word word : dictionary.getWords()) {
            if(word.getWordTarget().equals(wordTarget)) return word;
        }
        return null;
    }
    public boolean insertWordFromFile(Word word) {
        for (Word dictionaryWord : this.dictionary.getWords()) {
            if(dictionaryWord.getWordTarget().equals(word.getWordTarget()))
                return false;
        }
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/dictionary.txt", true);
            myWriter.write(String.format("%s\t%s\t%s\t%s\t%s\n", word.getWordTarget(), word.getWordExplain(), word.getWordForm(), word.getSpelling(), word.getExample()));
            myWriter.close();
            StringBuilder example = new StringBuilder();
            String [] examples = word.getExample().substring(1, word.getExample().length() - 1).split(", ");
            for (String s : examples) {
                example.append(s).append("\n");
            }
             word.setExample(example.toString());
            this.dictionary.getWords().add(word);
            System.out.println("Thêm từ thành công");
            return true;
        } catch (IOException exception) {
            System.out.println("Thêm từ không thành công");
            exception.printStackTrace();
            return false;
        }
    }
    public boolean removeWordFromFile(String value) {
        for (Word word : this.dictionary.getWords()) {
            if(word.getWordTarget().equals(value)) {
                try{
                    File file = new File("src/main/resources/dictionary.txt");
                    List<String> out = Files.lines(file.toPath())
                            .filter(line -> line.split("\t")[0].equals("value"))
                            .collect(Collectors.toList());
                    Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                    this.dictionary.getWords().remove(word);
                    return true;
                }
                catch(Exception e) {
                    System.out.println("Xóa từ không thành công!");
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    public boolean updateWordFromFile(String text, Word word) {
        for (Word dictionaryWord : dictionary.getWords()) {
            if(Objects.equals(text, dictionaryWord.getWordTarget())) {
                try {
                    File file = new File("src/main/resources/dictionary.txt");
                    List<String> out = Files.lines(file.toPath())
                            .map(line -> {
                                if (line.split("\t")[0].equals(text))
                                    return String.format("%s\t%s\t%s\t%s\t%s", word.getWordTarget(), word.getWordExplain(), word.getWordForm(), word.getSpelling(), word.getExample());
                                else
                                    return line;
                            })
                            .collect(Collectors.toList());
                    Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                    dictionaryWord.update(word);
                    System.out.println("Cập nhật từ thành công!");
                    return true;
                } catch (Exception e) {
                    System.out.println("Cập nhật từ không thành công!");
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    private String convertArrayToString(String array) {
        StringBuilder string = new StringBuilder("");
        List.of(array.substring(1, array.length() - 1).split(", ")).forEach( element -> {
            string.append(element).append("\n");
        });
        return string.toString();
    }
    public Dictionary getDictionary() {
        return this.dictionary;
    }
}
