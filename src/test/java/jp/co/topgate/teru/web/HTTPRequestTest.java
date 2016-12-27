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
        getRequestParameterHelper("test", "name", data);
        getRequestParameterHelper("sex", "man", data);
        getRequestParameterHelper("message", "hello, world", data);

        postData = "name=mike&sex=woman&body=hey&japanese=off";
        contentLength = postData.length();
        stringBuilder.setLength(0);
        stringBuilder.append("POST program/board HTTP/1.1" + CRLF);
        stringBuilder.append("Content-Length: " + String.valueOf(contentLength) + CRLF);
        stringBuilder.append(CRLF);
        stringBuilder.append(postData);
        data = stringBuilder.toString();
        getRequestParameterHelper("mike", "name", data);
        getRequestParameterHelper("woman", "sex", data);
        getRequestParameterHelper("hey", "message", data);
        getRequestParameterHelper("japanese", "off", data);
    }

    /**
     * getRequestParameter()のヘルパーメソッド
     * @param expected 期待値
     * @param name 取得したいパラメータ名
     * @param data パターンデータ
     */
    private void getRequestParameterHelper(String expected, String name, String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestBody = request.getRequestBody();
        assertThat(request.getRequestPamameter(name), is(expected));
    }


}