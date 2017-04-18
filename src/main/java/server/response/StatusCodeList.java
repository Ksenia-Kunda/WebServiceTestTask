package server.response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class StatusCodeList {

    private static final String CODE_200 = "200 OK";
    private static final String CODE_404 = "404 Not Found";
    private static final String CODE_400 = "400 Bad Request";

    private static final String START_PAGE_MESSAGE = "This is my server";
    private static final String FILE_CANT_BE_CREATED_MESSAGE = "File can't be created. Please, write location for the file.";
    private static final String FILE_NOT_FOUND_MESSAGE = "File not found.";

    private static final String METHOD_GET = "get";
    private static final String METHOD_POST = "post";
    private static final String METHOD_PUT = "put";
    private static final String METHOD_DELETE = "delete";

    Map<String, String [] > statusCodeList;

    public StatusCodeList(){
        statusCodeList = new LinkedHashMap<>();
    }

    public Map<String, String []> createStatusCodeListForStartPage() {
        statusCodeList.put(METHOD_GET, new String[]{CODE_200, START_PAGE_MESSAGE});
        statusCodeList.put(METHOD_POST, new String[]{CODE_400, FILE_CANT_BE_CREATED_MESSAGE});
        statusCodeList.put(METHOD_PUT, new String[]{CODE_404, FILE_NOT_FOUND_MESSAGE});
        statusCodeList.put(METHOD_DELETE, new String[]{CODE_404, FILE_NOT_FOUND_MESSAGE});
        return statusCodeList;
    }
}
