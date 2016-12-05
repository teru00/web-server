package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

//テストクラス
public class HTTPResponseTest {
    @Test
    public void getHeaders() {
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html\n");
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getheadersField(), CoreMatchers.is(buff.toString()));
    }
}