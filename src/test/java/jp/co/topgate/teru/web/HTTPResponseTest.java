package jp.co.topgate.teru.web;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HTTPResponseTest {

    @Test
    public void getHeadersField() {
        final String CRLF = "\r\n";
        StringBuilder buff = new StringBuilder();
        buff.append("Content-Type: text/html" + CRLF);
        HTTPResponse response = new HTTPResponse();
        response.setHeader("Content-Type", "text/html");
        assertThat(response.getHeadersField(), is(buff.toString()));
    }

    @Test
    public void getContentType() {
        HTTPResponse response = new HTTPResponse();
        Map<String, String> testMap = new HashMap<String, String>() {
            {
                put("test.html", "text/html");
                put("test.css", "text/css");
                put("test.png", "image/png");
                put("test.jpeg", "image/jpeg");
                put("test.gif", "image/gif");
                put("test.js", "application/javascript");
            }
        };

        for (String key : testMap.keySet()) {
            String contentType = response.getContentType(key);
            assertThat(contentType, is(testMap.get(key)));
        }
    }

    @Test
    public void getResponseMessage() throws UnsupportedEncodingException {

        String responseMessage = "HTTP/1.1 200 OK\n"
                + "Content Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html\n>"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <title>Simple HTTP Server</title>\n"
                + "        <link rel=\"stylesheet\" href=\"style.css\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Hello Simple HTTP Server</h1>\n"
                + "        <img src=\"donaldTrump.png\">\n"
                + "        <script src=\"myscript.js\"></script>\n"
                + "        <ul>\n"
                + "            <li><a href=\"hc.jpeg\">JPEG</a></li>\n"
                + "            <li><a href=\"dl.gif\">GIF</a></li>\n"
                + "            <li><a href=\"donaldTrump.png\">PNG</a></li>\n"
                + "        </ul>\n"
                + "    </body>\n"
                + "</html>";

        HTTPResponse response = new HTTPResponse();
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setHeader("Content Type", "text/html");
        String body = "<!DOCTYPE html\n>"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <title>Simple HTTP Server</title>\n"
                + "        <link rel=\"stylesheet\" href=\"style.css\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Hello Simple HTTP Server</h1>\n"
                + "        <img src=\"donaldTrump.png\">\n"
                + "        <script src=\"myscript.js\"></script>\n"
                + "        <ul>\n"
                + "            <li><a href=\"hc.jpeg\">JPEG</a></li>\n"
                + "            <li><a href=\"dl.gif\">GIF</a></li>\n"
                + "            <li><a href=\"donaldTrump.png\">PNG</a></li>\n"
                + "        </ul>\n"
                + "    </body>\n"
                + "</html>";
        response.setMessageBody(body.getBytes());
        String actual = new String(response.getResponseMessage(), "UTF-8");
        assertThat(actual, is(responseMessage));

    }

    @Test
    public void getStatusLine() {
        getStatusLinceHelper("HTTP/1.1 200 Ok", "200");
        getStatusLinceHelper("HTTP/1.1 404 not found", "404");
        getStatusLinceHelper("HTTP/1.1 200 Ok", "405");
    }
    // getStatusLine()のテストメソッドのヘルパー
    private void getStatusLinceHelper(String expected, String data) {
        HTTPResponse response = new HTTPResponse();
        response.setStatusLine(data);
        assertThat(response.getStatusLine(), is(expected));
    }
}