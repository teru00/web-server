package jp.co.topgate.teru.web;

import java.io.IOException;

/**
 *
 * リクエストオブジェクトを分析して、適切なハンドラを割りあてるクラス。
 */
class HandlerDispatch {

    /**
     * リクエストを適切なハンドラにディスパッチ（タスクを振る）する。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException 入出力の例外
     */
    HTTPResponse dispatch(HTTPRequest request) throws Exception {
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

        // わざわざ呼び出し元のThreadまで返してあげないといけないほどのものか。
        return response;
    }
}
