package jp.co.topgate.teru.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * サーバソケットで受信したクライアントと通信を行うためのスレッドクラス
 */
public class HTTPThread extends Thread {

    /**
     *
     * クライアントソケット
     */
    private final Socket socket;

    /**
     * コンストラクタ
     *
     * @param socket
     */
    public HTTPThread(Socket socket) {
        this.socket = socket;
    }

    /**
     *
     *
     */
    public void run() {
        try {
            InputStream inputStream = this.socket.getInputStream();

            HTTPRequest request = new HTTPRequest(inputStream);

            HTTPRequestHandler handler = new HTTPRequestHandler();

            HTTPResponse response = handler.handle(request);

            BufferedOutputStream bo = new BufferedOutputStream(socket.getOutputStream());

            byte[] responseMessage = response.getResponseMessage();
            System.out.println("レスポンスメッセージを取得しました。");

            if (responseMessage != null) {
                bo.write(responseMessage, 0, responseMessage.length);
                bo.flush();
                System.out.println("レスポンスメッセージを送信しました。");
                bo.close();
                System.out.println("ソケットを解放しました。");
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
