package jp.co.topgate.teru.web;

import java.io.IOException;

/**
 *
 * リクエストオブジェクトを分析して、適切なハンドラを割りあてるクラス。
 */
class HTTPRequestHandler {

    /**
     *
     * 受信したリクエストを分析して適切なハンドラにレスポンスを組み立てをデリゲートする。
     * リクエストURIに応じて、リソースファイルの読み込みを行う。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException 入出力の例外
     */
    HTTPResponse handle(HTTPRequest request) throws Exception {
        HTTPResponse response = new HTTPResponse();
        HandlerMap handlerMap = new HandlerMap();
        Handler handler = handlerMap.getHandler(request.getRequestURI());
        if (request.getRequestMethod().equals("GET")) {
            handler.doGET(request);
        } else if (request.getRequestURI().equals("POST")) {
            handler.doPOST(request);
        }


        if (request.getRequestURI().equals("/program/board")) {
            DynamicContentHandler dynamicContentHandler = new DynamicContentHandler();
            if (request.getRequestMethod().equals("GET")) {
                response = dynamicContentHandler.doGET(request);
            } else {
                response = dynamicContentHandler.doPOST(request);
            }
        } else {
            StaticContentHandler staticContentHandler = new StaticContentHandler();
            response = staticContentHandler.doGET(request);
        }

        return response;
    }
}
