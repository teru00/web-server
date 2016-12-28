package jp.co.topgate.teru.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author terufumi shimoji
 * TCPの受付、リクエスト単位でレスポンスを組み立てるハンドラオブジェクトを呼び出すクラス。
 * サーバのライフサイクルを管理する。
 */
public class Server {
    /**
     * ポート番号
     * サーバーソケットで利用するポートの番号
     */
    private static final int PORT = 8080;

    /**
     * メインメソッド
     */
    public static void main(String[] args) {
        try {
            //ServerSocketでリスナー用意
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Webサーバ起動" + " PORT:" + serverSocket.getLocalPort() + " >>>");

            while (true) {
                Socket socket = serverSocket.accept();
                HTTPThread httpThread = new HTTPThread(socket);
                httpThread.start();
                System.out.println("");
                System.out.println("新しいThreadを起動>>>");
            }
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
        System.out.println(">>>Webサーバをシャットダウン...");
    }
}
