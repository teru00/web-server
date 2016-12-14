package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;


/**
 * Created by e125761 on 2016/12/13.
 */
public class HTTPRequestHandlerTest {
    @Test
    public void errorHandle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html>\n");
        stringBuilder.append("    <head>\n");
        stringBuilder.append("        <meta charset=\"UTF-8\" />\n");
        stringBuilder.append("        <title>Simple HTTP Server</title>\n");
        stringBuilder.append("    <body>\n");
        stringBuilder.append("        <h1>404 Not Found</h1>\n");
        stringBuilder.append("    </body>\n");
        stringBuilder.append("</html>");

        String errorContent404 = new String(stringBuilder);

        stringBuilder.setLength(0);
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html>\n");
        stringBuilder.append("    <head>\n");
        stringBuilder.append("        <meta charset=\"UTF-8\" />\n");
        stringBuilder.append("        <title>Simple HTTP Server</title>\n");
        stringBuilder.append("    <body>\n");
        stringBuilder.append("        <h1>405 Method not allowed Explained</h1>\n");
        stringBuilder.append("    </body>\n");
        stringBuilder.append("</html>");

        String errorContent405 = new String(stringBuilder);

        errorHandleHelper(errorContent404, "404");
        errorHandleHelper(errorContent405, "405");

    }
    private void errorHandleHelper(String expeceted, String data) {
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler();
        String errorContent = httpRequestHandler.errorHandle(data);
        assertThat(errorContent, CoreMatchers.is(expeceted));
    }
}
