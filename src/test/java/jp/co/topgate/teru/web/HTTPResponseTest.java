package jp.co.topgate.teru.web;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HTTPResponseTest {

    @Test
    public void getHeadersField() {
        final String CRLF = "\r\n";
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html" + CRLF);
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), is(buff.toString()));
    }

    @Test
    public void getContentType() {
        HTTPResponse response = new HTTPResponse();
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