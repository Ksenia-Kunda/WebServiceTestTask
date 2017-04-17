package server;

import book.Book;
import book.BookListMaker;
import converter.JsonConverter;
import converter.XmlConverter;

import java.io.*;
import java.util.List;

/**
 * Created by Ksenia on 13.04.2017.
 */
public class ResponseMessenger {

    private RequestHandler request;
    private String response;
    private String path;
    private BookListMaker bookListMaker;
    private List<Book> bookList;
    private JsonConverter jsonConverter;
    private XmlConverter xmlConverter;

    private static final String ROOT = "/src/main/resources/";

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";
    private static final String CODE_400 = "400 Bad Request";
    private static final String CODE_201 = "201 Created";

    private static final String START_PAGE_MESSAGE = "This is my server";
    private static final String FILE_CANT_BE_CREATED_MESSAGE = "File can't be created. Please, write location for the file.";
    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private static final String FILE_EXISTS_MESSAGE = "File already exists";
    private static final String FILE_DELETED_MESSAGE = "File deleted";
    private static final String FILE_CREATED_MESSAGE = "File created";
    private static final String FILE_UPDATED_MESSAGE = "File updated";

    private static final String CONTENT_TYPE_XML = ".xml";


    public ResponseMessenger(RequestHandler request) {
        this.request = request;
        path = System.getProperty("user.dir") + ROOT + request.getPathUPI();
    }

    public String getResponse() {
        return response;
    }

    public void httpMethod() {
        switch (request.getMessage()) {
            case "GET":
                if (request.getPathUPI().equals("/")) {
                    responseMessage(START_PAGE_MESSAGE, CODE_200);
                } else {
                    httpMethodGet();
                }
                break;
            case "POST":
                if (request.getPathUPI().equals("/")) {
                    responseMessage(FILE_CANT_BE_CREATED_MESSAGE, CODE_400);
                } else {
                    if (request.ifHeaderFileNameIsPresent()) {
                        httpMethodPostThroughReadyFile(request.getBodyMessage());
                    } else {
                        httpMethodPostThroughContent();
                    }
                }
                break;
            case "PUT":
                if (request.getPathUPI().equals("/")) {
                    responseMessage(FILE_NOT_FOUND_MESSAGE, CODE_404);
                } else {
                    if (request.ifHeaderFileNameIsPresent()) {
                        httpMethodPutThroughReadyFile(request.getBodyMessage());
                    } else {
                        httpMethodPutThroughContent();
                    }
                }
                break;
            case "DELETE":
                httpMethodDelete();
                break;
        }
    }

    public void responseMessage(String messageText, String statusCode) {
        response = "HTTP/1.1 " + statusCode + " \r\n" +
                "Server: My local server \r\n" +
                "Content-Type: " + request.getAcceptHeaderValue() + "\r\n" +
                "Content-Length: " + messageText.length() + " \r\n" +
                "Connection: Closed \r\n" +
                "\r\n" + messageText;
    }

    public void httpMethodGet() {
        File file = new File(path + request.getFileFormat());
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                int s;
                while ((s = fileInputStream.read()) != -1) {
                    response += (char) s;
                }
                responseMessage(response, CODE_200);
                System.out.println(response);
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            responseMessage(FILE_NOT_FOUND_MESSAGE, CODE_404);
            System.out.println(response);
        }
    }

    public void httpMethodPostThroughReadyFile(String bodyMessage) {
        try {
            File file = new File(path + request.getContentType());
            if (file.exists()) {
                responseMessage(FILE_EXISTS_MESSAGE, CODE_400);
            } else {
                FileWriter fileWriter = new FileWriter(path + request.getContentType());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(bodyMessage);
                bufferedWriter.close();
                responseMessage(FILE_CREATED_MESSAGE, CODE_201);
            }
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpMethodPostThroughContent() {
        File file = new File(path + request.getContentType());
        if (file.exists()) {
            responseMessage(FILE_EXISTS_MESSAGE, CODE_400);
        } else {
            bookListMaker = new BookListMaker(request.getBodyMessage());
            bookList = bookListMaker.createBookMap();
            if (request.getContentType().equals(CONTENT_TYPE_XML)) {
                xmlConverter = new XmlConverter(bookList);
                xmlConverter.getReadyFile();
                responseMessage(FILE_CREATED_MESSAGE, CODE_201);
            } else {
                jsonConverter = new JsonConverter(bookList);
                httpMethodPostThroughReadyFile(jsonConverter.getReadyFile());
            }
            System.out.println(response);
        }
    }

    public void httpMethodDelete() {
        File file = new File(path + request.getFileFormat());
        if (file.exists()) {
            if (file.exists()) {
                file.delete();
                responseMessage(FILE_DELETED_MESSAGE, CODE_200);
            }
        } else {
            responseMessage(FILE_NOT_FOUND_MESSAGE, CODE_404);
            System.out.println(response);

        }
    }

    public void httpMethodPutThroughReadyFile(String bodyMessage) {
        try {
            File file = new File(path + request.getContentType());
            if (!file.exists()) {
                responseMessage(FILE_NOT_FOUND_MESSAGE, CODE_404);
            } else {
                FileWriter fileWriter = new FileWriter(path + request.getContentType());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(bodyMessage);
                bufferedWriter.close();
                responseMessage(FILE_UPDATED_MESSAGE, CODE_200);
            }
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpMethodPutThroughContent() {

        File file = new File(path + request.getContentType());
        if (!file.exists()) {
            responseMessage(FILE_NOT_FOUND_MESSAGE, CODE_404);
        } else {
            bookListMaker = new BookListMaker(request.getBodyMessage());
            bookList = bookListMaker.createBookMap();
            if (request.getContentType().equals(CONTENT_TYPE_XML)) {
                xmlConverter = new XmlConverter(bookList);
                xmlConverter.getReadyFile();
                responseMessage(FILE_CREATED_MESSAGE, CODE_201);
            } else {
                jsonConverter = new JsonConverter(bookList);
                httpMethodPostThroughReadyFile(jsonConverter.getReadyFile());
            }
        }
        System.out.println(response);
    }
}
