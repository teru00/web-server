package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HTTPResponseTest {

    @Test
    public void getHeadersField() {
        final String CRLF = "\r\n";
        HTTPResponse response = new HTTPResponse(new ByteArrayOutputStream());
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), is("Content-Type: text/html" + CRLF));
    }

    @Test
    public void getContentType() {
        OutputStream outputStream = new ByteArrayOutputStream();
        HTTPResponse response = new HTTPResponse(outputStream);

        Map<String, String> testMap = new HashMap<String, String>() {
            {
                put("test.html", "text/html");
                put("test.css", "text/css");
                put("test.png", "image/png");
                put("test.jpeg", "image/jpeg");
                put("test.gif", "image/gif");
                put("test.js", "application/javascript");
            }
        };

        for (String key : testMap.keySet()) {
            String contentType = response.getContentType(key);
            assertThat(contentType, is(testMap.get(key)));
        }
    }
}