package configuration;


import org.testng.*;
import org.testng.annotations.BeforeClass;

import static com.jayway.restassured.RestAssured.*;


/**
 * Created by Ksenia on 18.04.2017.
 */
public class RestAssuredConfiguration {


    @BeforeClass
    public void setUpRestAssured() {
        System.out.println("BEFORE");
        String portDefault = System.getProperty("server.port");

        if (portDefault == null){
            port = Integer.valueOf(8080);
        }else {
            port = Integer.valueOf(portDefault);
        }

        String basePathDefault = System.getProperty("server.base");
        if (basePathDefault == null) {
            basePathDefault = "/book_list";
        }
        basePath=basePathDefault;

        String baseHostDefault = System.getProperty("server.host");
        if (baseHostDefault == null) {
            baseHostDefault = "http://localhost";
        }
        baseURI = baseHostDefault;
    }
}
