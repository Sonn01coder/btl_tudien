import java.util.List;

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
        System.out.println("Nạp từ từ file dictionary.txt!");
        dictionaryManagement.insertFromFile();
        System.out.println("Tất cả các từ là:");
        showAllWord();
        dictionaryManagement.dictionaryLookup(); // tra cứu bằng dòng lệnh
    }
}
