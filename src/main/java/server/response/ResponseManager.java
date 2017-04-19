package server.response;

import methods.StartLocation;
import methods.otherLocation.MethodDelete;
import methods.otherLocation.MethodGet;
import methods.otherLocation.MethodPost;
import methods.otherLocation.MethodPut;
import server.RequestHandler;

/**
 * Created by Ksenia on 13.04.2017.
 */
public class ResponseManager {

    private RequestHandler request;
    private String responseMessage;
    private String path;
    private StartLocation startLocation;
    private MethodGet methodGet;
    private MethodPost methodPost;
    private MethodPut methodPut;
    private MethodDelete methodDelete;

    private static final String ROOT = "/src/main/resources";

    private static final String METHOD_GET = "get";
    private static final String METHOD_POST = "post";
    private static final String METHOD_PUT = "put";
    private static final String METHOD_DELETE = "delete";

    public ResponseManager(RequestHandler request) {
        this.request = request;
        path = System.getProperty("user.dir") + ROOT + request.getPathUPIWithoutParameter();
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
        startLocation = new StartLocation(request.getMethod(), request.getAcceptHeaderValue());
        responseMessage = startLocation.responseFromStartPage();
    }

    public void httpMethod() {
        switch (request.getMethod()) {
            case METHOD_GET:
                methodGet = new MethodGet(request, path);
                responseMessage = methodGet.executeMethodGet();
                break;
            case METHOD_POST:
                methodPost = new MethodPost(request, path);
                responseMessage = methodPost.executeMethodPost();
                break;
            case METHOD_PUT:
                methodPut = new MethodPut(request, path);
                responseMessage = methodPut.executeMethodPut();
                break;
            case METHOD_DELETE:
                methodDelete = new MethodDelete(request, path);
                responseMessage = methodDelete.executeMethodDelete();
                break;
        }
    }
}
