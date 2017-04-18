package server;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Ksenia on 17.04.2017.
 */
public class Response {

    private static final String START_LINE = "HTTP/1.1 ";
    private static final String HEADER_SERVER = "Server: ";
    private static final String HEADER_SERVER_VALUE = "My local server";
    private static final String HEADER_CONTENT_TYPE = "Content-Type: ";
    private static final String HEADER_CONTENT_LENGTH = "Content-Length: ";
    private static final String HEADER_CONNECTION = "Connection: ";
    private static final String HEADER_CONNECTION_VALUE = "Closed";

    private static final String SEPARATOR = "\n";

    private Map<String, String> responseLinesMap;
    private StringBuffer stringBuffer;

    public Response() {
        responseLinesMap = new LinkedHashMap<>();
        stringBuffer = new StringBuffer();
    }

    public String setResponseMessage(String statusCode, String contentTypeValue, Integer contentLengthValue, String responseMessage) {
        fillResponseLinesMap(statusCode, contentTypeValue, contentLengthValue);
        stringBuffer = createResponseString().append(SEPARATOR).append(responseMessage);
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

    private void fillResponseLinesMap(String statusCode, String contentTypeValue, Integer contentLengthValue) {
        responseLinesMap.put(START_LINE, statusCode);
        responseLinesMap.put(HEADER_SERVER, HEADER_SERVER_VALUE);
        responseLinesMap.put(HEADER_CONTENT_TYPE, contentTypeValue);
        responseLinesMap.put(HEADER_CONTENT_LENGTH, contentLengthValue.toString());
        responseLinesMap.put(HEADER_CONNECTION, HEADER_CONNECTION_VALUE);
    }

    private StringBuffer createResponseString() {
        Iterator<Map.Entry<String, String>> iterator = responseLinesMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String> pair = iterator.next();
            stringBuffer = stringBuffer.append(pair.getKey()).append(pair.getValue()).append(SEPARATOR);
        }
        return stringBuffer;
    }

}
