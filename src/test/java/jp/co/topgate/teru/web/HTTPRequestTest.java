package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {

    private String requestMessage = "GET / HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Encoding: gzip, deflate, sdch, br\n" +
            "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";

    //リクエストメソッドを正しく取得できているか判定
    @Test
    public void getRequestMethod() {
        InputStream inputStream = new ByteArrayInputStream(this.requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is("GET"));
    }

    //リクエストURIを正しく取得できているか判定
    @Test
    public void getRequestURI() {
        InputStream inputStream = new ByteArrayInputStream(this.requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestURI = request.getRequestURI();
        assertEquals("/", requestURI);
    }

    //init()のテスト
    @Test
    public void init() {
        InputStream inputStream = new ByteArrayInputStream(this.requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String[] expect = {"GET", "/", "HTTP/1.1"};
        assertThat(request.getRequestLine(), is(expect));
    }
}