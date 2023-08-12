import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DictionaryManagement {
    private final Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    // insert từ bằng dòng lệnh
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
    // insert từ bởi file
    public void insertFromFile() {
        try {
            URL resource = this.getClass().getClassLoader().getResource("dictionary.txt"); // Lấy đường dẫn file trong thư mục resources`
            File file = new File(resource.toURI()); // Tạo đối tượng file dùng để đọc file
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) { // Duyệt từng hàng trong file
                String data = myReader.nextLine();
                String wordTarget = data.split("\t")[0]; // Tách chuỗi data bởi dấu tab thành mảng string gồm 2 phần tử, phân tử 1 là từ, phần tử 2 là nghĩa của từ đó
                String wordExplain = data.split("\t")[1];
                Word word = new Word(wordTarget, wordExplain);
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
    // Xóa từ bởi command
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
    // edit từ bởi command
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
    // Tìm kiếm từ chính xác
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
    // Xuất file
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

    public Dictionary getDictionary() {
        return this.dictionary;
    }
}
