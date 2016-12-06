package jp.co.topgate.teru.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * クライアントから取得したHTTPリクエストの状態や
 * レスポンスを送信するための振る舞いを持つクラス。
 */
public class HTTPRequest {

    /**
     * HTTPリクエストの1行目
     * 最低限のWebサーバはこのリクエストラインを解析できればよい。
     */
    private String[] requestLine;

    /**
     * inputStreamを使ってHTTPRequestオブジェクトに初期値を設定する。
     * @param inputStream ソケットから取得した入力ストリーム
     */
    public HTTPRequest(InputStream inputStream) {
        //コンストラクタでインスタンスメソッドを読んでよいのか
        this.init(inputStream);
    }

    /**
     * HTTPRequestオブジェクトの初期値を設定するインスタンスメソッド。
     * @param inputStream
     */
    public void init(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("今から入力ストリーム経由でデータを取得します");
        try {
            String line = br.readLine();
            this.requestLine = line.split(" ");

            System.out.println("リクエストメッセージ===========");
            System.out.println(line);
            while(line != null  && !line.isEmpty()) {
                line = br.readLine();
                System.out.println(line);
            }
            System.out.println("リクエストメッセージ===========");

        } catch (IOException e) {
            System.err.println("ERROR: "+ e);
            e.getStackTrace();
        }
    }

    /**
     *
     * リクエストメソッドのゲッター
     * 現時点ではGETかその他の判定をする
     * @return
     */
    public String getRequestMethod() {
        return this.requestLine[0];
    }

    /**
     * リクエストURIのゲッター
     * Webリソースの読み込みに必要なURIを返すインスタンスメソッド
     * @return
     */
    public String getRequestURI() {
        String requestURI;
        if (requestLine[1].contains("?")) {
            requestURI = this.requestLine[1].substring(0, requestLine[1].indexOf("?"));
        } else {
            requestURI = this.requestLine[1];
        }
        return requestURI;
    }

    /**
     * リクエストURIを分析してリソースまでの適切なパス名を返す
     * @return resourcePath
     */
    public String getResourcePath() {
        String resourcePath;
        String requestURI = this.getRequestURI();
        if (requestURI.endsWith("/")) {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/") + "index.html";
        } else {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/");
        }
        return resourcePath;
    }

    /**
     * テスト用メソッド
     */
    public String[] getRequestLine() {
        return this.requestLine;
    }
}
