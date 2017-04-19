package methods.otherLocation;

import server.RequestHandler;
import server.response.Response;
import java.io.File;


/**
 * Created by Ksenia on 18.04.2017.
 */
public class MethodDelete {

    private RequestHandler request;
    private String responseMessage;
    private String path;
    private Response response;
    private File file;

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private static final String FILE_DELETED_MESSAGE = "File deleted";

    public MethodDelete(RequestHandler request, String path){
        this.request = request;
        this.path = path;
        response = new Response();
    }

    public String executeMethodDelete() {
            file = new File(path + request.getFileFormat());
            if (file.exists()) {
                file.delete();
                responseMessage = response.setResponseMessage(CODE_200, request.getAcceptHeaderValue(), FILE_DELETED_MESSAGE.length(), FILE_DELETED_MESSAGE);
            } else {
                responseMessage = response.setResponseMessage(CODE_404, request.getAcceptHeaderValue(), FILE_NOT_FOUND_MESSAGE.length(), FILE_NOT_FOUND_MESSAGE);
        }
        return responseMessage;
    }
}
