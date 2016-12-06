package jp.co.topgate.teru.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by terufumishimoji on 2016/12/06.
 */
public class HTTPThread extends Thread {

    /**
     *
     * クライアントソケット
     */
    private Socket socket;

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
            //クライアントソケットからデータソースのIOを扱うストリーム取得
            InputStream inputStream = this.socket.getInputStream();

            //リクエストオブジェクトを生成
            HTTPRequest request = new HTTPRequest(inputStream);

            //リクエストを処理する
            HTTPRequestHandler handler = new HTTPRequestHandler();

            //HTTPRequestは参照型なので引数に渡してもおｋ
            HTTPResponse response = handler.handle(request);

            //レスポンスを返す準備
            BufferedOutputStream bo = new BufferedOutputStream(socket.getOutputStream());

            //レスポンスメッセージを取得する
            byte[] responseMessage = response.getResponseMessage();
            System.out.println("レスポンスメッセージを取得しました。");

            //Socketに書き込んでいく。
            if (responseMessage != null) {
                bo.write(responseMessage, 0, responseMessage.length);
                //バッファに残っているもの強制的に書き込み
                bo.flush();
                System.out.println("レスポンスメッセージを送信しました。");
                //ストリームに関連したSocketを解放
                bo.close();
                System.out.println("ソケットを解放しました。");
            }
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
