package restTemplateTests;

import book.Book;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kseniya_Kunda on 4/20/2017.
 */
public class ResponseMessageTest {

    @Test
    public void successfulGetXml() {

        RestTemplate restTemplate = new RestTemplate();

        HttpMessageConverter<Book> httpMessageConverter = new AbstractXmlHttpMessageConverter<Book>() {
            @Override
            protected Book readFromSource(Class<? extends Book> aClass, HttpHeaders httpHeaders, Source source) throws IOException {
                return null;
            }

            @Override
            protected void writeToResult(Book book, HttpHeaders httpHeaders, Result result) throws IOException {

            }

            @Override
            protected boolean supports(Class<?> aClass) {
                return false;
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        String bookLocation = "http://localhost:8080/book_list_for_GET";
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(bookLocation, HttpMethod.GET, request, String.class);
        Assert.assertEquals(response.getBody(), "");
    }
}
