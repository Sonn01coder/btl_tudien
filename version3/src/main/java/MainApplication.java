import java.net.URISyntaxException;

public class MainApplication {
    public static void main(String[] args) throws URISyntaxException {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.dictionryAdvanced();// nạp word từ file
        dictionaryCommandline.showAllWord(); // show các từ
        dictionaryCommandline.removeWord(); // Xóa từ
        dictionaryCommandline.showAllWord(); // show kết quả sau khi xóa
        dictionaryCommandline.dictionarySearcher(); // Tìm kiếm các từ gần đúng
        System.out.println("Thực hiện xuất file: export_words.txt!");
        dictionaryCommandline.exportFile(); // export file
    }
}
