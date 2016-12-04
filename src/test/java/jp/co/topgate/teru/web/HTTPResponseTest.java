package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

//テストクラス
public class HTTPResponseTest {
    @Test
    public void testGetHeaders() {
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html\n");
        HTTPResponse response = new HTTPResponse();
        response.setResponseHeader("Content-Type", "text/html");
        assertThat(response.getResponseHeaders(), CoreMatchers.is(buff.toString()));
    }
}