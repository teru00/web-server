package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {

    // インターフェースによる実装もある。
    @Test
    public void getRequestMethod() {
        getRequestMethodHelper("GET", "GET / HTTP/1.1");
        getRequestMethodHelper("POST", "POST / HTTP/1.1");
    }

    /**
     * 幾つかのパターンをテストするために用意したヘルパーメソッド
     * h-はヘルパーという意味のprefix
     *
     * @param expected 期待値
     * @param data テストデータ
     */
    private void getRequestMethodHelper(String expected, String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is(expected));
    }

    @Test
    public void getRequestURI() {
        //テストデータ
        getRequestURIHelper("/", "GET / HTTP/1.1");
        getRequestURIHelper("/index.html", "GET /index.html HTTP/1.1");
        getRequestURIHelper("/sample/index.html", "GET /sample/index.html HTTP/1.1");
        getRequestURIHelper("/", "GET /?hoge=hoge HTTP/1.1");
    }

    /**
     * getRequestURIのデータパターンをテストするヘルパーメソッド
     * @param expected
     * @param data
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

    //init()のテスト
    @Test
    public void init() {
        // data テストデータ
        String requestMessage = "GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, sdch, br\n" +
                "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";
        // expected 期待値
        String[] expected = {"GET", "/", "HTTP/1.1"};

        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        assertThat(request.getRequestLine(), is(expected));
    }
}