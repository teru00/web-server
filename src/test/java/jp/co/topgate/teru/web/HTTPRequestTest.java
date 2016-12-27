package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {
    static final String CRLF = "\n\r";

    // インターフェースによる実装もある。
    @Test
    public void getRequestMethod() {
        getRequestMethodHelper("GET", "GET / HTTP/1.1");
        getRequestMethodHelper("POST", "POST / HTTP/1.1");
    }

    /**
     * 幾つかのパターンをテストするために用意したヘルパーメソッド
     *
     * @param expected 期待値
     * @param data     テストデータ
     */
    private void getRequestMethodHelper(String expected, String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is(expected));
    }

    @Test
    public void getRequestURI() {
        getRequestURIHelper("/", "GET / HTTP/1.1");
        getRequestURIHelper("/index.html", "GET /index.html HTTP/1.1");
        getRequestURIHelper("/sample/index.html", "GET /sample/index.html HTTP/1.1");
        getRequestURIHelper("/", "GET /?hoge=hoge HTTP/1.1");
    }

    /**
     * getRequestURIのデータパターンをテストするヘルパーメソッド
     *
     * @param expected 期待値
     * @param data テストデータ
     */
    private void getRequestURIHelper(String expected, String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestURI = null;
        try {
            requestURI = request.getRequestURI();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.getStackTrace();
        }
        assertThat(expected, is(requestURI));
    }

    @Test
    public void getResourcePath() {
        getResourcePathHelper("src/main/resources/index.html", "GET / HTTP/1.1");
        getResourcePathHelper("src/main/resources/index.html", "GET /index.html HTTP/1.1");
        getResourcePathHelper("src/main/resources/sample", "GET /sample HTTP/1.1");
        getResourcePathHelper("src/main/resources/sample/index.html", "GET /sample/ HTTP/1.1");
        getResourcePathHelper("src/main/resources/sample/index.html", "GET /sample/index.html HTTP/1.1");
        getResourcePathHelper("src/main/resources/sample/index.html", "GET /sample/////// HTTP/1.1");
        getResourcePathHelper("src/main/resources/sample/index.html", "GET /sample////////index.html HTTP/1.1");
    }

    private void getResourcePathHelper(String expected, String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        assertThat(request.getResourcePath(), is(expected));
    }

    @Test
    public void getRequestParameter() {
        String postData = "name=bob&sex=man&message=hello, world";
        int contentLength = postData.length();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POST program/board HTTP/1.1" + CRLF);
        stringBuilder.append("Content-Length: " + String.valueOf(contentLength) + CRLF);
        stringBuilder.append(CRLF);
        stringBuilder.append(postData);
        String data = stringBuilder.toString();
        getRequestParameterHelper("bob", "name", data, postData);
        getRequestParameterHelper("man", "sex", data, postData);
        getRequestParameterHelper("hello, world", "message", data, postData);

        postData = "name=mike&sex=woman&message=hey&japanese=off";
        contentLength = postData.length();
        stringBuilder.setLength(0);
        stringBuilder.append("POST program/board HTTP/1.1" + CRLF);
        stringBuilder.append("Content-Length: " + String.valueOf(contentLength) + CRLF);
        stringBuilder.append(CRLF);
        stringBuilder.append(postData);
        data = stringBuilder.toString();
        getRequestParameterHelper("mike", "name", data, postData);
        getRequestParameterHelper("woman", "sex", data, postData);
        getRequestParameterHelper("hey", "message", data, postData);
        getRequestParameterHelper("off", "japanese", data, postData);
    }

    private void getRequestParameterHelper(String expected, String name, String data, String postData) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        request.setRequestBody(postData);
        assertThat(request.getRequestParameter(name), is(expected));
    }
}