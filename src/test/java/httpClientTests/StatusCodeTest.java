package httpClientTests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ksenia on 19.04.2017.
 */
public class StatusCodeTest {

    private static final String ROOT = "/src/test/resources/book_list_for_put.xml";

    @Test
    public void unsuccessfulDelete() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete("http://localhost:8080/book_list_nonexistent");
            CloseableHttpResponse response = httpclient.execute(httpDelete);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void successfulPut() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            File file = new File(System.getProperty("user.dir")+ROOT);
            FileEntity entity = new FileEntity(file, ContentType.create("application/xml"));
            HttpPut httpPut = new HttpPut("http://localhost:8080/book_list");
            httpPut.addHeader("Filename", "book_list_for_update");
            httpPut.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPut);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
