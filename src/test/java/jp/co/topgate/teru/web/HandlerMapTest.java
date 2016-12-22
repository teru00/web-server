package jp.co.topgate.teru.web;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by terufumishimoji on 2016/12/21.
 */
public class HandlerMapTest {

    /**
     * urlパターンを投入して、期待するハンドラが帰ってくるかテストする。
     * パターンの集合を用意する。
     */
    @Test
    public void getHandler() {
        /**
         * 静的、動的コンテンツハンドラオブジェクトを用意
         */
        Handler staticContentHandler = new StaticContentHandler();
        Handler dynamicContentHandler = new DynamicContentHandler();

        getHandlerHelper(staticContentHandler, "");
        getHandlerHelper(staticContentHandler, "/");
        getHandlerHelper(staticContentHandler, "/index.html");
        getHandlerHelper(staticContentHandler, "/sample");
        getHandlerHelper(staticContentHandler, "program/");
        getHandlerHelper(staticContentHandler, "program/board");
        getHandlerHelper(dynamicContentHandler, "program/board/");
    }

    /**
     * getHandler()のヘルパーメソッド
     * @param expected 期待値
     * @param data URI
     */
    private void getHandlerHelper(Handler expected, String data) {
        HandlerMap handlerMap = new HandlerMap();
        // data（パターン）を投入して、ハンドラを取り出す。
        Handler handler = handlerMap.getHandler(data);
        assertThat(handler.getClass().getName(), is(expected.getClass().getName()));
    }
}
