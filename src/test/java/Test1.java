import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
/**
 * Created by Ksenia on 18.04.2017.
 */
public class Test1 {


    @Test
    public void testTest(){
        given().
                when().
                get("/book_list").
                thenReturn();
    }
}
