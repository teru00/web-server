package jp.co.topgate.teru.web;

import java.io.IOException;

/**
 *
 * リクエストオブジェクトを分析して、適切なハンドラを割りあてるクラス。
 */
class HandlerDispatch {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    /**
     * リクエストを適切なハンドラにディスパッチ（タスクを振る）する。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException 入出力の例外
     */
    HTTPResponse dispatch(HTTPRequest request, HTTPResponse response) throws Exception {

        HandlerMap handlerMap = new HandlerMap();
        Handler handler = handlerMap.getHandler(request.getRequestURI());

        // "GET"はマッジクワード？なので定数化します。
        if (request.getRequestMethod().equals(METHOD_GET)) {
            handler.handleGet(request, response);
        } else if (request.getRequestURI().equals(METHOD_POST)) {
            //handler.handlePost(request);
        }

        return response;
    }
}

// リフレクションの導入
