package methods.otherLocation;

import book.Book;
import book.BookListMaker;
import converter.JsonParser;
import server.RequestHandler;
import server.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class MethodGet {

    private RequestHandler request;
    private String path;
    private BookListMaker bookListMaker;
    private String responseMessage;
    private List<Book> bookList;
    private JsonParser jsonParser;
    private File file;
    private Response response;

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book not found.";

    public MethodGet(RequestHandler request, String path){
        this.request = request;
        this.path = path;
        bookListMaker = new BookListMaker();
        response = new Response();
    }

    public String executeMethodGet() {
        if (request.getPathUPI().contains("?")) {
            responseMessage = httpMethodGetWithParameter();
        } else {
            responseMessage = httpMethodGetForAllList();
        }
        return responseMessage;
    }

    private String httpMethodGetWithParameter() {
        bookListMaker = new BookListMaker();
        String parameterValue = "";
        String[] allParameters = request.getParameter().split(",");
        for (String parameter : allParameters) {
            if (parameter.startsWith("id")) {
                parameterValue = parameter.split("=")[1];
                file = new File(path + request.getFileFormat());
                if (file.exists()) {
                    jsonParser = new JsonParser(path + request.getFileFormat());
                    bookList = jsonParser.parseFromFile();
                    responseMessage = bookListMaker.getBookThroughId(bookList, parameterValue);
                    if (responseMessage.equals(" ")){
                        responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), BOOK_NOT_FOUND_MESSAGE.length(), BOOK_NOT_FOUND_MESSAGE);
                    }else {
                        responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), responseMessage.length(), responseMessage);
                    }
                } else {
                    responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
                }
            }
        }
        return responseMessage;
    }

    private String httpMethodGetForAllList() {
        file = new File(path + request.getFileFormat());
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int s;
                while ((s = fileInputStream.read()) != -1) {
                    responseMessage += (char) s;
                }
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), responseMessage.length(), responseMessage);
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
        }
        return responseMessage;
    }
}
