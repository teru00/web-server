package jp.co.topgate.teru.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * リクエストオブジェクトを分析して、適切なレスポンスを組み立てるハンドラクラス。
 */
class HTTPRequestHandler {

    /**
     *
     * 受信したリクエストを分析して適切なレスポンスを組み立てるためのインスタンスメソッド。
     * リクエストURIに応じて、リソースファイルの読み込みを行う。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException 入出力の例外
     */
    HTTPResponse handle(HTTPRequest request) throws Exception {

        // 静的コンテンツ処理か動的コンテンツ処理かの判断をする
        // 判断はリクエストのURIに依存する。
        // <リクエストURIにprogramがマッチ>
        // YES ==> 動的
        // NO ==> 静的
        // ブロック内でお互いのハンドラインスタンスを作ってあげれば良い。
        // ここで問題なのが、 リクエストの状態はその後のハンドラに依存するということ
        // つまりここでも渡してあげないといけないというめんどくささ。レスポンスの場合は
        // ハンドラの呼び出し先のThreadでrespond処理をしているので、レスポンスの実態は
        // ハンドラに返さないといけない。

        HTTPResponse response = new HTTPResponse();

        if (request.getRequestMethod() == "program") {
            // 動的アプリケーション
            DynamicContentHandler dynamicContentHandler = new DynamicContentHandler();
            // GET/POST
            if (request.getRequestMethod() == "GET") {
                response = dynamicContentHandler.doGET();
            } else {
                response = dynamicContentHandler.doPOST();
            }
        } else {
            // 静的
            // レスポンスを受け取りたい
            StaticContentHandler staticContentHandler = new StaticContentHandler();
            response = staticContentHandler.doGET(request);
        }

        return response;
    }
}
