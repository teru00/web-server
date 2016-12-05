package jp.co.topgate.teru.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author terufumi shimoji
 * サーバークラス
 */
public class Server {
    /**
     * ポート番号
     */
    private static final int PORT = 8080;
    //private static final String CRLF = "\n";

    /**
     * メインメソッド：JVMが最初にロードするプログラム
     */
    public static void main(String[] args) {
        System.out.println("Webサーバ起動>>>");
        try {
            //ServerSocketでリスナー用意
            ServerSocket serverSocket = new ServerSocket(PORT);

            //（改善点）TCP受付スレッドとリクエスト処理スレッドを分割すべき
            while (true) {
                //クライアントソケット取得
                Socket socket = serverSocket.accept();

                //クライアントソケットからデータソースのIOを扱うストリーム取得
                InputStream inputStream = socket.getInputStream();

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

                //Socketに書き込んでいく。
                if (responseMessage != null) {
                    bo.write(responseMessage, 0, responseMessage.length);
                    //バッファに残っているもの強制的に書き込み
                    bo.flush();
                    bo.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        System.out.println(">>>Webサーバをシャットダウン...");
    }
}
