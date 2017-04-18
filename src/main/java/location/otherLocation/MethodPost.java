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
public class MethodPost {

    private RequestHandler request;
    private String responseMessage;
    private String path;
    private BookListMaker bookListMaker;
    private List<Book> bookList;
    private JsonConverter jsonConverter;
    private XmlConverter xmlConverter;
    private Response response;
    private File file;

    private static final String CODE_400 = "400 Bad Request";
    private static final String CODE_201 = "201 Created";

    private static final String FILE_EXISTS_MESSAGE = "File already exists";
    private static final String FILE_CREATED_MESSAGE = "File created";

    private static final String CONTENT_TYPE_XML = ".xml";

    public MethodPost(RequestHandler request, String path) {
        this.request = request;
        this.path = path;
        response = new Response();
    }

    public String executeMethodPost() {
        if (request.ifHeaderFileNameIsPresent()) {
            httpMethodPostThroughReadyFile();
        } else {
            httpMethodPostThroughContent();
        }
        return responseMessage;
    }

    private String httpMethodPostThroughReadyFile() {
        try {
            file = new File(path + request.getContentTypeValue());
            if (file.exists()) {
                responseMessage = response.setResponseMessage(CODE_400, request.getAcceptHeaderValue(), FILE_EXISTS_MESSAGE.length(), FILE_EXISTS_MESSAGE);
            } else {
                FileWriter fileWriter = new FileWriter(path + request.getContentTypeValue());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(request.getBodyMessage());
                bufferedWriter.close();
                responseMessage = response.setResponseMessage(CODE_201, request.getAcceptHeaderValue(), FILE_CREATED_MESSAGE.length(), FILE_CREATED_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    private String httpMethodPostThroughContent() {
        try {
            file = new File(path + request.getContentTypeValue());
            if (file.exists()) {
                responseMessage = response.setResponseMessage(CODE_400, request.getAcceptHeaderValue(), FILE_EXISTS_MESSAGE.length(), FILE_EXISTS_MESSAGE);
            } else {
                bookListMaker = new BookListMaker(request.getBodyMessage());
                bookList = bookListMaker.createBookMap();
                if (request.getContentTypeValue().equals(CONTENT_TYPE_XML)) {
                    xmlConverter = new XmlConverter(bookList);
                    xmlConverter.getReadyFile();
                    responseMessage = response.setResponseMessage(CODE_201, request.getAcceptHeaderValue(), FILE_CREATED_MESSAGE.length(), FILE_CREATED_MESSAGE);
                } else {
                    jsonConverter = new JsonConverter(bookList);
                    FileWriter fileWriter = new FileWriter(path + request.getContentTypeValue());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(jsonConverter.getReadyFile());
                    bufferedWriter.close();
                    responseMessage = response.setResponseMessage(CODE_201, request.getAcceptHeaderValue(), FILE_CREATED_MESSAGE.length(), FILE_CREATED_MESSAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }
}