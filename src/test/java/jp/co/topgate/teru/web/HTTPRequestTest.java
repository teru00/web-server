package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {

    //リクエストメソッドを正しく取得できているか判定
    @Test
    public void getRequestMethod() {
        String requestMessage = "GET / HTTP/1.1";
        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(rm);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is("GET"));
    }

    //リクエストURIを正しく取得できているか判定
    @Test
    public void getRequestURI() {
        String requestMessage = "GET /search/store/index.html HTTP/1.1";
        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(rm);
        String requestURI = request.getRequestURI();
        assertEquals("/search/store/index.html", requestURI);
    }
}