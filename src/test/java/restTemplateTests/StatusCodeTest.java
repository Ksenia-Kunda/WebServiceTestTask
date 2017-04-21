package restTemplateTests;

import book.Book;
import book.BookListMaker;
import org.apache.http.HttpStatus;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ksenia on 19.04.2017.
 */
public class StatusCodeTest {

    @Test
    public void successfulPostBookList() {

        RestTemplate restTemplate = new RestTemplate();

        String book = "Author: Shildt \n" +
                "Language: java \n" +
                "Edition: eight";
        MultiValueMap<String, String> headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(book, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/book_list", request, String.class);

        Assert.assertEquals(response.getStatusCode().value(), 201);
    }

    @Test
    public void successfulGetBookList() {

            RestTemplate restTemplate = new RestTemplate();

            String book = "http://localhost:8080/book_list_for_GET_with_parameter?id=2";

            ResponseEntity <String> response = restTemplate.getForEntity(book, String.class);
            Assert.assertEquals(response.getStatusCode().value(), 200);

    }
}
