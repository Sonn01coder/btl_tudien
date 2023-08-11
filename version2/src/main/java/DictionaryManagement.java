import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DictionaryManagement {
    private final Dictionary dictionary;
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    // insert từ bằng bằng commandline
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
    //insert từ từ file dictionary.txt trong thư mục resource
    public void insertFromFile() {
        try {
            URL resource = this.getClass().getResource("dictionary.txt"); // Lấy đường dẫn file trong thư mục resources`
            File file = new File(resource.toURI());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) { // đọc file từng dòng sau đó tách dòng bởi dấu tab
                String data = myReader.nextLine();
                String wordTarget = data.split("\t")[0];
                String wordExplain = data.split("\t")[1];
                Word word = new Word(wordTarget, wordExplain);
                dictionary.getWords().add(word);
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
    // Tìm kiếm từ muốn tìm kiếm
    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ muốn tìm kiếm: ");
        String word = scanner.next();
        for(Word element : this.dictionary.getWords()) {
            if(element.getWordTarget().equals(word)) {
                System.out.format("Kết quả từ cần tìm là:\n%s: %s\n", element.getWordTarget(), element.getWordExplain());
                return;
            }
        }
        System.out.println("Không tìm thấy từ cần tìm!");
    }
    public Dictionary getDictionary() {
        return this.dictionary;
    }
}
