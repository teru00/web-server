package jp.co.topgate.teru.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertThat;


/**
 * Created by e125761 on 2016/12/13.
 */
public class HTTPRequestHandlerTest {
    @Test
    public void errorHandle() {
        errorHandleHelper("1", "1");
    }
    private void errorHandleHelper(String expeceted, String result) {
        assertThat(result, CoreMatchers.is(expeceted));
    }
}
