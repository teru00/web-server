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
    public void readFile() {
        readFileHelper("テスト\n", "src/test/resources/test.html");
        readFileHelper("テスト\n", "src/test/resources/.sample/test.html");
        readFileHelper("テスト\n", "src/test/resources/sample/test.html");
    }
    private void readFileHelper(String expeceted, String path) {
        // ファイルの中身を読み込むことができているかを確認する
        // Fileを作る
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler();
        File file = new File(path);
        byte[] byteContent = new byte[0];
        try {
            byteContent = httpRequestHandler.readFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strContent = null;
        try {
            strContent = new String(byteContent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertThat(strContent, CoreMatchers.is(expeceted));
    }
}
