package location.otherLocation;

import book.Book;
import book.BookListMaker;
import converter.JsonConverter;
import converter.JsonParser;
import converter.XmlConverter;
import location.StartLocation;
import server.RequestHandler;
import server.response.Response;

import java.io.*;
import java.util.List;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class MethodPut {

    private RequestHandler request;
    private String responseMessage;
    private String path;
    private BookListMaker bookListMaker;
    private List<Book> bookList;
    private JsonConverter jsonConverter;
    private XmlConverter xmlConverter;
    private Response response;
    private File file;

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private static final String FILE_UPDATED_MESSAGE = "File updated";

    private static final String CONTENT_TYPE_XML = ".xml";

    public MethodPut(RequestHandler request, String path){
        this.request = request;
        this.path = path;
        bookListMaker = new BookListMaker();
        response = new Response();
    }

    public String executeMethodPut() {
        if (request.ifHeaderFileNameIsPresent()) {
            httpMethodPutThroughReadyFile();
        } else {
            httpMethodPutThroughContent();
        }
        return responseMessage;
    }

    private String httpMethodPutThroughReadyFile() {
        try {
            file = new File(path + request.getContentTypeValue());
            if (!file.exists()) {
                responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
            } else {
                FileWriter fileWriter = new FileWriter(path + request.getContentTypeValue());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(request.getBodyMessage());
                bufferedWriter.close();
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_UPDATED_MESSAGE.length(), FILE_UPDATED_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    private String httpMethodPutThroughContent() {
        file = new File(path + request.getContentTypeValue());
        if (!file.exists()) {
            responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
        } else {
            bookListMaker = new BookListMaker(request.getBodyMessage());
            bookList = bookListMaker.createBookMap();
            if (request.getContentTypeValue().equals(CONTENT_TYPE_XML)) {
                xmlConverter = new XmlConverter(bookList);
                xmlConverter.getReadyFile();
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_UPDATED_MESSAGE.length(), FILE_UPDATED_MESSAGE);
            } else {
                try {
                jsonConverter = new JsonConverter(bookList);
                FileWriter fileWriter = new FileWriter(path + request.getContentTypeValue());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonConverter.getReadyFile());
                bufferedWriter.close();
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_UPDATED_MESSAGE.length(), FILE_UPDATED_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseMessage;
    }

}
