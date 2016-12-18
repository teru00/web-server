package jp.co.topgate.teru.web;

import jp.co.topgate.teru.web.http.HTTPRequest;
import jp.co.topgate.teru.web.http.HTTPResponse;

import java.io.InputStream;
import java.net.Socket;

/**
 *
 * サーバソケットで受信したクライアントと通信を行うためのスレッドクラス
 */
class HTTPThread extends Thread {

    /**
     *
     * クライアントソケット
     */
    private final Socket socket;

    /**
     * コンストラクタ
     *
     * @param socket クライアントソケット
     */
    HTTPThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * スレッドを起動する
     *
     */
    public void run() {
        try {
            InputStream inputStream = this.socket.getInputStream();

            HTTPRequest request = new HTTPRequest(inputStream);

            HTTPRequestHandler handler = new HTTPRequestHandler();

            HTTPResponse response = handler.handle(request);

            // メソッドごとにハンドラ処理を対応させる
            // クライアントとTCP通信のやり取りを行うHTTPThreadにリクエストメソッドの振り分けをさせている。
            // リクエストメソッドの振り分けはどこでやるべきなのか？
            // リクエストメソッドの主な種類は4つ。get post delete put 任意（ファストリーなどは任意なものを使っている）
            // getはwebリソースの要求
            // postはフォームからデータを送信する。データはリクエストとして送信されてくるのだが、
            // データを保持する場所はリクエストURIのリクエストパラメータになる。


            response.respond(socket.getOutputStream());

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
