package location;

import server.Response;

import java.util.Map;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class StartPageLocation {

    private String methodName;
    private String contentTypeValue;
    private Response response;
    private StatusCodeList statusCodeList;

    public StartPageLocation(String methodName, String contentTypeValue){
        this.methodName = methodName;
        this.contentTypeValue = contentTypeValue;
        response = new Response();
        statusCodeList = new StatusCodeList();
    }

    public String responseFromStartPage() {
        String responseResult="";
        statusCodeList.createStatusCodeListForStartPage();
        for (Map.Entry<String, String[] > pair : statusCodeList.createStatusCodeListForStartPage().entrySet())
        {
            if (methodName.equals(pair.getKey())){
                responseResult = response.setResponseMessage(pair.getValue()[0], contentTypeValue, pair.getValue()[1].length(), pair.getValue()[1]);
            }

        }
        return responseResult;
    }
}
