package converter;

import book.Book;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Created by Ksenia on 17.04.2017.
 */
public class JsonConverter {

    private List<Book> bookList;
    private String json;
    private Gson gson;

    public JsonConverter(List<Book> bookList) {
        this.bookList = bookList;
        gson = new Gson();
        json = "";
    }

    public String getReadyFile(){
            json =gson.toJson(bookList);
            return json;
    }
}
