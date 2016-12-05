package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertThat;

//テストクラス
public class HTTPResponseTest {

    @Test
    public void setStatusLine() throws IllegalAccessException {
        try {
            HTTPResponse response = new HTTPResponse();
            String statusLine = "HTTP/1.1 200 OK";

            Field field = response.getClass().getDeclaredField("statusLine");
            field.setAccessible(true);
            response.setStatusLine(statusLine);

            assertThat(statusLine, CoreMatchers.is((String)field.get(response)));
        } catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }

    @Test
    public void getHeadersField() {
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html\n");
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), CoreMatchers.is(buff.toString()));
    }
}