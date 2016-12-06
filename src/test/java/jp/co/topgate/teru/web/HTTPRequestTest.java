package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HTTPRequestTest {

    private String requestMessage1 = "GET / HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Encoding: gzip, deflate, sdch, br\n" +
            "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";

    private String requestMessage2 = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Encoding: gzip, deflate, sdch, br\n" +
            "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";

    private String requestMessage3 = "GET /index.html?hoge=hoge HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Encoding: gzip, deflate, sdch, br\n" +
            "Accept-Language: ja,en-US;q=0.8,en;q=0.6\n";

    String[] requestMessage = {requestMessage1, requestMessage2, requestMessage3};

    //繰り返し処理をしやすいようにイテレータにする
    //List li = Arrays.asList(requestMessage);
    //Iterator<String> it = li.iterator();

    //リクエストメソッドを正しく取得できているか判定
    @Test
    public void getRequestMethod() {
        InputStream inputStream = new ByteArrayInputStream(this.requestMessage[1].getBytes());
        HTTPRequest request = new HTTPRequest(inputStream);
        String requestMethod = request.getRequestMethod();
        assertThat(requestMethod, is("GET"));
    }

    //リクエストURIを正しく取得できているか判定
//    @Test
//    public void getRequestURI() {
//        String[] requestURIs = new String[6];
//        requestURIs[0] = "/";
//        requestURIs[1] = "/";
//        requestURIs[2] = "/test.html";
//        requestURIs[3] = "/test.html";
//        requestURIs[4] = "/sample/te";
//        requestURIs[5] = "/sample/test.html";
//
//        for (it.hasNext()) {
//            InputStream inputStream = new ByteArrayInputStream(it.next().getBytes());
//            HTTPRequest request = new HTTPRequest(inputStream);
//            String requestURI = request.getRequestURI();
//            assertEquals("/", requestURI);
//        }
//    }
//
//    @Test
//    public void getResourcePath() {
//        //テストデータ準備
//        // / /test.html /test.css /sample/ /sample/test.html /sample/test.css /.sample/ /sample
//        //期待値準備
//        // /index.html
//        //レスポンスオブジェクト生成
//        //レスポンスオブジェクトにURIを設定
//        //getResourcePathを呼び出す
//        //アサーションする
//
//    }
//
//    //init()のテスト
//    @Test
//    public void init() {
//        InputStream inputStream = new ByteArrayInputStream(this.requestMessage.getBytes());
//        HTTPRequest request = new HTTPRequest(inputStream);
//        String[] expect = {"GET", "/", "HTTP/1.1"};
//        assertThat(request.getRequestLine(), is(expect));
//    }
//}