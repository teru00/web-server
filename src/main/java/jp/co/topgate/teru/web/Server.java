package jp.co.topgate.teru.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 8080;
    //private static final String CRLF = "\n";

    public static void main(String[] args) {
        System.out.println("Running My WebServer");
        try {
            //ServerSocketでリスナー用意
            ServerSocket serverSocket = new ServerSocket(PORT);

            //リクエストを待ち続ける
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
                    bo.write(responseMessage);
                    //バッファに残っているもの強制的に書き込み
                    bo.flush();
                    bo.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("ShutDown My WebServer");
    }
}
