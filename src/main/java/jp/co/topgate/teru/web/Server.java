package jp.co.topgate.teru.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

import jp.co.topgate.teru.web.HTTPRequestHandler;

public class Server {
    public static void main(String[] args) {
        try {
            //ServerSocketでリスナー用意
            //クライアントソケット取得
            //クライアントソケットからデータソースのIOを扱うストリーム取得

            ServerSocket serverSocket = new ServerSocket();
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            HTTPRequest request = new HTTPRequest(inputStream);

            //リクエストを処理する
            //HTTPRequestHandler
            System.out.println(request);


        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
