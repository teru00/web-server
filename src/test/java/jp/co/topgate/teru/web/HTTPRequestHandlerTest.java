package jp.co.topgate.teru.web;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HTTPRequestHandlerTest {
    @Test
    public void testHandle() {
        HTTPRequestHandler handler = new HTTPRequestHandler();
        String requestMessage = "GET / HTTP/1.1";
        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
        HTTPRequest request = new HTTPRequest(rm);
        String result = handler.handle(request);
        assertEquals("success", result);
    }
}
