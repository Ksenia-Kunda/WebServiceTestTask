package server;


/**
 * Created by Ksenia on 13.04.2017.
 */
public class RequestHandler {

    private String[] lines;
    private static final String HEADER_ACCEPT = "Accept: ";
    private static final String HEADER_FILE_NAME = "Filename: ";
    private static final String HEADER_CONTENT_TYPE = "Content-Type: ";
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_XML = "application/xml";
    private static final String FORMAT_JSON = ".json";
    private static final String FORMAT_XML = ".xml";
    private String acceptHeaderValue;
    private String contentType;

    public RequestHandler(String requestString) {
        lines = requestString.split("\r\n");
        for (String l : lines) {
            System.out.println(l);
        }
    }

    private String[] getStartLine() {
        return lines[0].split(" ");
    }

    public String getMessage() {
        return getStartLine()[0];
    }

    public String getPathUPI() {
        return getStartLine()[1];
    }

    public String getFileFormat() {
        String format = "";
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].startsWith(HEADER_ACCEPT)) {
                if (lines[i].endsWith(ACCEPT_JSON)) {
                    format = FORMAT_JSON;
                    acceptHeaderValue = ACCEPT_JSON;
                } else {
                    if (lines[i].endsWith(ACCEPT_XML)) {
                        format = FORMAT_XML;
                        acceptHeaderValue = ACCEPT_XML;
                    } else {
                        format = FORMAT_JSON;
                        acceptHeaderValue = ACCEPT_JSON;
                    }
                }
                break;
            }
        }
        return format;
    }

    public String getAcceptHeaderValue() {
            getFileFormat();
        return acceptHeaderValue;
    }

    public String getBodyMessage() {
        String message = "";
        int lineIndex = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() == 0) {
                lineIndex = i + 1;
            }
        }
        if (lineIndex != 0) {
            for (int i = lineIndex; i < lines.length; i++) {
                message = lines[i] + "\n";
            }
        }
        return message;
    }

    public String getContentType() {
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].startsWith(HEADER_CONTENT_TYPE)) {
                contentType = "." + lines[i].replace(HEADER_CONTENT_TYPE, "").split("/")[1];
                break;
            }

        }
        if (!(contentType.equals(FORMAT_JSON) || contentType.equals(FORMAT_XML))){
            contentType = FORMAT_JSON;
        }
        return contentType;
    }

    public Boolean ifHeaderFileNameIsPresent(){
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].startsWith(HEADER_FILE_NAME)){
                return true;
            }
        }
        return false;
    }
}