package jp.co.topgate.teru.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by terufumishimoji on 2016/11/22.
 */
public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;

        //待機状態にして、接続要求があると、Client socketを取得する。
        socket = serverSocket.accept();

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //brを使ってrequestを作っていく。

        //handlerを初期化して
        //responseを取得する。
        HTTPRequestHandler handler = null;
        HTTPRequest request = null;

        response = handler.handle(request);
        //responseをクライアントに送信する
        response.send();

    }
}
