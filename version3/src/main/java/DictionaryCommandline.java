import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    public void showAllWord() {
        System.out.format("%-30s%-30s%-30s\n", "No", "English", "Vietnamese");
        List<Word> words = dictionaryManagement.getDictionary().getWords();
        for(int i = 1; i <= words.size(); i++ ) {
            System.out.format("%-30s%-30s%-30s\n", i, words.get(i - 1).getWordTarget(), words.get(i - 1).getWordExplain());
        }
    }
    public void dictionaryBasic(){
        dictionaryManagement.insertFromCommandline();
        showAllWord();
    }
    public void dictionryAdvanced() {
        dictionaryManagement.insertFromFile();
    }
    public void exportFile() {
        dictionaryManagement.dictionaryExportToFile();
    }
    public void dictionarySearcher() {
        System.out.print("Nhập từ cần tìm kiếm: ");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        List<String> lists = new ArrayList<>();
        dictionaryManagement.getDictionary().getWords().forEach(e -> {
            if(e.getWordTarget().startsWith(word)) {
                lists.add(e.getWordTarget());
            }
        });
        System.out.println(lists);
    }
    public void removeWord() {
        this.dictionaryManagement.deleteFromCommandLine();
    }
}
