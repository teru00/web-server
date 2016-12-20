package jp.co.topgate.teru.web;

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

            HandlerDispatch handlerDispatch = new HandlerDispatch();
            // ディスパッチの処理はハンドラにHTTP処理を委譲（元々はこのクラスが処理をする権限を持っていた）する役割を持っている。
            // デリゲート先は引数で取得したリクエストのURIにあらかじめマッピング定義をされているハンドラが呼ばれる。
            // マッピング定義されていないURI（例外）の場合は、
            HTTPResponse response = handlerDispatch.dispatch(request);

            response.respond(socket.getOutputStream());

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
