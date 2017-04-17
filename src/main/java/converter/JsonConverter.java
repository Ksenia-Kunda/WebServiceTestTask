package converter;

import book.Book;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ksenia on 17.04.2017.
 */
public class JsonConverter {

    private List<Book> bookMap;
    private String json;
    private Gson gson;

    public JsonConverter(List<Book> bookMap) {
        this.bookMap = bookMap;
        gson = new Gson();
        json = "";
    }

    public String getReadyFile(){
            json =gson.toJson(bookMap);
            return json;
    }
}
