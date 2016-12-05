package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

//テストクラス
public class HTTPResponseTest {

    @Test
    public void setStatusLine() {
        try {
            HTTPResponse response = new HTTPResponse();
            String statusLine = "HTTP/1.1 200 OK";

            Field field = response.getClass().getDeclaredField("statusLine");
            field.setAccessible(true);
            response.setStatusLine(statusLine);

            assertThat(statusLine, CoreMatchers.is((String)field.get(response)));
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

//    @Test
//    public void setHeader() {
//        try {
//            HTTPResponse response = new HTTPResponse();
//            String statusLine =
//        } catch (Exception e) {
//            System.err.println(e);
//            e.printStackTrace();
//        }
//    }
    @Test
    public void getHeadersField() {
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html\n");
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), CoreMatchers.is(buff.toString()));
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
            assertThat(contentType, CoreMatchers.is(testMap.get(key)));
        }
    }
}