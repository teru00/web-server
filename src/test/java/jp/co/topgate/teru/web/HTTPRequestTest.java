package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {

    @Test
    public void getRequestMethod() {
        //テストデータ
        String requestMessage1 = "GET / HTTP/1.1";
        String requestMessage2 = "POST / HTTP/1.1";
        String[] requestMessage = {requestMessage1, requestMessage2};
        //期待値
        String method1 = "GET";
        String method2 = "POST";
        String[] method = {method1, method2};


        for (int i = 0; i < requestMessage.length; i++) {
            InputStream inputStream = new ByteArrayInputStream(requestMessage[i].getBytes());
            HTTPRequest request = new HTTPRequest(inputStream);
            String requestMethod = request.getRequestMethod();
            assertThat(requestMethod, is(method[i]));
        }
    }

    @Test
    public void getRequestURI() {
        //テストデータ
        String requestMessage1 = "GET / HTTP/1.1";
        String requestMessage2 = "GET /index.html HTTP/1.1";
        String requestMessage3 = "GET /sample/index.html HTTP/1.1";
        String requestMessage4 = "GET /?hoge=hoge HTTP/1.1";
        String[] requestMessage = {requestMessage1, requestMessage2, requestMessage3, requestMessage4};

        //期待値
        String expect1 = "/";
        String expect2 = "/index.html";
        String expect3 = "/sample/index.html";
        String expect4 = "/";
        String[] expect = {expect1, expect2, expect3, expect4};

        for (int i = 0; i < requestMessage.length; i++) {
            InputStream inputStream = new ByteArrayInputStream(requestMessage[i].getBytes());
            HTTPRequest request = new HTTPRequest(inputStream);
            String requestURI = request.getRequestURI();
            assertThat(expect[i], CoreMatchers.is(requestURI));
        }
    }

    @Test
    public void getResourcePath() {
        //テストデータのパターン
        String requestMessage1 = "GET / HTTP/1.1";
        String requestMessage2 = "GET /index.html HTTP/1.1";
        String requestMessage3 = "GET /sample HTTP/1.1";
        String requestMessage4 = "GET /sample/ HTTP/1.1";
        String requestMessage5 = "GET /sample/index.html HTTP/1.1";
        String requestMessage6 = "GET /sample//////// HTTP/1.1";
        String requestMessage7 = "GET /sample////////index.html HTTP/1.1";


        String[] requestMessage = {requestMessage1, requestMessage2, requestMessage3,
        requestMessage4, requestMessage5, requestMessage6, requestMessage7};

        //期待値パターン
        String expect1 = "src/main/resources/index.html";
        String expect2 = "src/main/resources/index.html";
        String expect3 = "src/main/resources/sample";
        String expect4 = "src/main/resources/sample/index.html";
        String expect5 = "src/main/resources/sample/index.html";
        String expect6 = "src/main/resources/sample/index.html";
        String expect7 = "src/main/resources/sample/index.html";

        String[] expect = {expect1, expect2, expect3, expect4, expect5, expect6, expect7};

        for (int i = 0; i < requestMessage.length; i++) {
            InputStream inputStream = new ByteArrayInputStream(requestMessage[i].getBytes());
            HTTPRequest request = new HTTPRequest(inputStream);

            assertThat(request.getResourcePath(), CoreMatchers.is(expect[i]));
        }
    }

    //init()のテスト
    @Test
    public void init() {
        //テストデータ
        String requestMessage = "GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, sdch, br\n" +
                "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";
        //期待値
        String[] expect = {"GET", "/", "HTTP/1.1"};

        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        assertThat(request.getRequestLine(), CoreMatchers.is(expect));
    }
}