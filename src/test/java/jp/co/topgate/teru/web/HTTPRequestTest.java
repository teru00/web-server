package jp.co.topgate.teru.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HTTPRequestTest {
    //リクエストインスタン生成
    //引数にはDUMMYのリクエストメッセージを渡す
    //リクエストメッセージはStringで書いて、inputStreamに変換する。
    //そしてgetRequestMessageを呼びだす。
    @Test
    public void testGetRequestMethod() {
        String requestMessage = "GET / HTTP/1.1";
        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(rm);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is("GET"));
    }

    @Test
    public void testGetRequestURI() {
        String requestMessage = "GET /search/store/index.html HTTP/1.1";
        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(rm);
        String requestURI = request.getRequestURI();
        assertEquals("/search/store/index.html", requestURI);
    }
}