package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terufumishimoji on 2016/12/19.
 */
public class HandlerMap {
    // マップフィールド
    // コンストラクタ
    // ゲッター

    private Map<String, Handler> handlerMap;

    public HandlerMap() {
        Map<String, Handler> handlerMap = new HashMap<String, Handler>();
        Handler dynamicContentHandler = new DynamicContentHandler();
        Handler staticContentHandler = new StaticContentHandler();
        // このようにハンドラーの初期化を行う。
        // URIとマッピング処理を行う。
        handlerMap.put("program/board", dynamicContentHandler);
        handlerMap.put("/", staticContentHandler);

        this.handlerMap = handlerMap;
    }

    public Handler getHandler(String url) {
        Handler handler = this.handlerMap.get(url);
        return handler;
    }
}
