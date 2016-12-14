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

            HTTPRequestHandler handler = new HTTPRequestHandler();

            HTTPResponse response = handler.handle(request);

            response.respond(socket.getOutputStream());

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
