import java.util.Scanner;
import java.util.stream.IntStream;

public class DictionaryManagement {
    private final Dictionary dictionary;
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    // chèn từ commandline
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
    public Dictionary getDictionary() {
        return this.dictionary;
    }
}
