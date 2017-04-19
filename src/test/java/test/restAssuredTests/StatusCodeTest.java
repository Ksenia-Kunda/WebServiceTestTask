package test.restAssuredTests;

import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;

/**
 * Created by Ksenia on 18.04.2017.
 */
public class StatusCodeTest {

    @Test
    public void successfulGetStartPage(){
        Response book = given().when().get().thenReturn();

        Assert.assertEquals(book.getStatusCode(), 200);
    }

    @Test
    public void successfulGetBookList(){
        Response book = given().when().get("/book_list").thenReturn();

        Assert.assertEquals(book.getStatusCode(), 200);
    }

    @Test
    public void unsuccessfulGet(){
        Response book = given().when().get("/book_list_nonexistent").thenReturn();

        Assert.assertEquals(book.getStatusCode(), 404);
    }



//    @Test
//    public void successfulGetMessage(){
//        Response book = given().when().get().thenReturn();
//
//        Assert.assertEquals(book.getBody().print(), "This is my server");
//    }
}
