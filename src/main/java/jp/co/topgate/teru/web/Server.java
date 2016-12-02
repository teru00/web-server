package jp.co.topgate.teru.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

import jp.co.topgate.teru.web.HTTPRequestHandler;

public class Server {

    //サーバクラスのフィールド
    //private static final int PORT = 8080;
    private static final String CRLF = "\r\n";

    public static void main(String[] args) {
        System.out.println("Running My WebServer");
        try {
            //ServerSocketでリスナー用意
            ServerSocket serverSocket = new ServerSocket();

            //クライアントソケット取得
            Socket socket = serverSocket.accept();

            //クライアントソケットからデータソースのIOを扱うストリーム取得
            InputStream inputStream = socket.getInputStream();

            //リクエストオブジェクトを生成
            HTTPRequest request = new HTTPRequest(inputStream);

            //リクエストを処理する
            System.out.println(request);

            //レスポンスを返す準備
            BufferedOutputStream bo = new BufferedOutputStream(socket.getOutputStream());

            //レスポンスの構成要素
            String line = "HTTP/1.1 200 OK " + CRLF;
            String headers = "Content-Type: text/html " + CRLF;
            String crlf = CRLF;
            String body = "<h1>Hello, World</h1>";

            //Socketに書き込んでいく。
            bo.write(line.getBytes());
            bo.write(headers.getBytes());
            bo.write(crlf.getBytes());
            bo.write(body.getBytes());

        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("ShutDown My WebServer");
    }
}
