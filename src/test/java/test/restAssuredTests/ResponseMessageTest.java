package test.restAssuredTests;

import book.Book;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Kseniya_Kunda on 4/20/2017.
 */
public class ResponseMessageTest {

    @Test
    public void successfulGetMessage() {
        Response book = given().when().get().thenReturn();

        Assert.assertEquals(book.getBody().print(), "This is my server");
    }

    @Test
    public void successfulGetMessageWithParameter() {
        Book book = new Book();
        book.setId("3");
        book.setAuthor("Bruce Eckel");
        book.setLanguage("java");
        book.setEdition("fourth");
        String id = "id=3";
        Response requiredBook = given().pathParam("id", id).when().get("/book_list_for_GET_with_parameter?{id}").thenReturn();
        Assert.assertEquals(requiredBook.getBody().print(), book.toString());
    }
}
