import org.junit.BeforeClass;
import static com.jayway.restassured.RestAssured.*;


/**
 * Created by Ksenia on 18.04.2017.
 */
public class TestBase {


    @BeforeClass
    public void setUpRestAssured() {
        String portDefault = System.getProperty("server.port");

        if (portDefault == null){
            port = Integer.valueOf(8080);
        }else {
            port = Integer.valueOf(portDefault);
        }

        baseURI = "http://localhost";
    }
}
