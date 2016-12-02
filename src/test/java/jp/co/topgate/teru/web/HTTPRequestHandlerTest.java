//package jp.co.topgate.teru.web;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//import org.hamcrest.CoreMatchers;
//import org.junit.Test;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//public class HTTPRequestHandlerTest {
//    @Test
//    public void testHandle() {
//        HTTPRequestHandler handler = new HTTPRequestHandler();
//        String requestMessage = "GET / HTTP/1.1";
//        InputStream rm = new ByteArrayInputStream(requestMessage.getBytes());
//        HTTPRequest request = new HTTPRequest(rm);
//        String result = handler.handle(request);
////        assertThat("", is());
//        assertEquals("success", result);
//    }
//}
