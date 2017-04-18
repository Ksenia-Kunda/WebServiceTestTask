package converter;

import book.Book;
import book.BookListMaker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class JsonParser {

   private List<Book> bookList;
    private Gson gson;
    private String path;

    public JsonParser(String path) {
        this.path = path;
        gson = new Gson();
    }

    public List<Book> parseFromFile() {
        try { FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bookList = gson.fromJson(bufferedReader.readLine(), new TypeToken<List<Book>>() {}.getType());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}