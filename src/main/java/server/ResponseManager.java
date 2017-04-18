package server;

import book.Book;
import book.BookListMaker;
import converter.JsonConverter;
import converter.JsonParser;
import converter.XmlConverter;
import location.StartPageLocation;

import java.io.*;
import java.util.List;

/**
 * Created by Ksenia on 13.04.2017.
 */
public class ResponseManager {

    private RequestHandler request;
    private String responseMessage;
    private String path;
    private BookListMaker bookListMaker;
    private List<Book> bookList;
    private JsonConverter jsonConverter;
    private XmlConverter xmlConverter;
    private JsonParser jsonParser;
    private Response response;
    private StartPageLocation startPageLocation;

    private static final String ROOT = "/src/main/resources";

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";
    private static final String CODE_400 = "400 Bad Request";
    private static final String CODE_201 = "201 Created";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private static final String FILE_EXISTS_MESSAGE = "File already exists";
    private static final String FILE_DELETED_MESSAGE = "File deleted";
    private static final String FILE_CREATED_MESSAGE = "File created";
    private static final String FILE_UPDATED_MESSAGE = "File updated";

    private static final String CONTENT_TYPE_XML = ".xml";

    public ResponseManager(RequestHandler request) {
        this.request = request;
        path = System.getProperty("user.dir") + ROOT + request.getPathUPIWithoutParameter();
        response = new Response();
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void choseDirection() {
        if (request.getPathUPI().equals("/")) {
            startPageActionManager();
        } else {
            httpMethod();
        }
    }

    public void startPageActionManager() {
        startPageLocation = new StartPageLocation(request.getMethod(), request.getAcceptHeaderValue());
        responseMessage = startPageLocation.responseFromStartPage();
    }

    public void httpMethod() {
        switch (request.getMethod()) {
            case "get":
                    if (request.getPathUPI().contains("?")){
                        httpMethodGetWithParameter();
                    } else {
                        httpMethodGetForAllList();
                }
                break;
            case "post":
                    if (request.ifHeaderFileNameIsPresent()) {
                        httpMethodPostThroughReadyFile(request.getBodyMessage());
                    } else {
                        httpMethodPostThroughContent();
                }
                break;
            case "put":
                    if (request.ifHeaderFileNameIsPresent()) {
                        httpMethodPutThroughReadyFile(request.getBodyMessage());
                    } else {
                        httpMethodPutThroughContent();
                }
                break;
            case "delete":
                    httpMethodDelete();
                break;
        }
    }

    private void httpMethodGetWithParameter(){
        bookListMaker = new BookListMaker();
        String parameterValue="";
        String [] allParameters = request.getParameter().split(",");
        for (String parameter:allParameters) {
            if (parameter.startsWith("id")){
                parameterValue = parameter.split("=")[1];
                File file = new File(path + request.getFileFormat());
                if (file.exists()) {
                    jsonParser = new JsonParser(path + request.getFileFormat());
                    bookList = jsonParser.parseFromFile();
                    responseMessage = bookListMaker.getBookThroughId(bookList, parameterValue);
                    responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), responseMessage.length(), responseMessage);

                }
            }
        }

    }

    private void httpMethodGetForAllList() {
        File file = new File(path + request.getFileFormat());
        System.out.println(request.getFileFormat());
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
    }

    private void httpMethodPostThroughReadyFile(String bodyMessage) {
        try {
            File file = new File(path + request.getContentTypeValue());
            if (file.exists()) {
                responseMessage = response.setResponseMessage(CODE_400, request.getAcceptHeaderValue(), FILE_EXISTS_MESSAGE.length(), FILE_EXISTS_MESSAGE);
            } else {
                makeFileIfReadyFile(bodyMessage);
                responseMessage = response.setResponseMessage(CODE_201, request.getAcceptHeaderValue(), FILE_CREATED_MESSAGE.length(), FILE_CREATED_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void httpMethodPostThroughContent() {
        File file = new File(path + request.getContentTypeValue());
        if (file.exists()) {
            responseMessage = response.setResponseMessage(CODE_400, request.getAcceptHeaderValue(),  FILE_EXISTS_MESSAGE.length(), FILE_EXISTS_MESSAGE);
        } else {
           makeFileIfContent(CODE_201, FILE_CREATED_MESSAGE);
        }
    }

    private void httpMethodDelete() {
        File file = new File(path + request.getFileFormat());
        if (file.exists()) {
            file.delete();
            responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_DELETED_MESSAGE.length(), FILE_DELETED_MESSAGE);
        } else {
            responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
        }
    }

    private void httpMethodPutThroughReadyFile(String bodyMessage) {
        try {
            File file = new File(path + request.getContentTypeValue());
            if (!file.exists()) {
                responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
            } else {
                makeFileIfReadyFile(bodyMessage);
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_UPDATED_MESSAGE.length(), FILE_UPDATED_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void httpMethodPutThroughContent() {
        File file = new File(path + request.getContentTypeValue());
        if (!file.exists()) {
            responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
        } else {
           makeFileIfContent(CODE_200, FILE_UPDATED_MESSAGE);
            }
        }

    private void makeFileIfContent(String statusCode, String message){
        bookListMaker = new BookListMaker(request.getBodyMessage());
        bookList = bookListMaker.createBookMap();
        if (request.getContentTypeValue().equals(CONTENT_TYPE_XML)) {
            xmlConverter = new XmlConverter(bookList);
            xmlConverter.getReadyFile();
            responseMessage = response.setResponseMessage(statusCode, request.getAcceptHeaderValue(), message.length(), message);
        } else {
            jsonConverter = new JsonConverter(bookList);
            httpMethodPostThroughReadyFile(jsonConverter.getReadyFile());
        }
    }

    private void makeFileIfReadyFile(String bodyMessage) throws IOException {
        FileWriter fileWriter = new FileWriter(path + request.getContentTypeValue());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(bodyMessage);
        bufferedWriter.close();
    }
}
