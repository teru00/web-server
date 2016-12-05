package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

//テストクラス
public class HTTPResponseTest {
    @Test
    public void getHeadersField() {
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html\n");
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), CoreMatchers.is(buff.toString()));
    }
}