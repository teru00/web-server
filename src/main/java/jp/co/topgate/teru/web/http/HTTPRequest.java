package jp.co.topgate.teru.web.http;

import java.io.*;
import java.net.URLDecoder;

/**
 *
 * クライアントから取得したHTTPリクエストの状態や
 * レスポンスを送信するための振る舞いを持つクラス。
 */
public class HTTPRequest {
    
    private String requestMethod;
    private String url;

    /**
     * inputStreamを使ってHTTPRequestオブジェクトに初期値を設定する。
     * @param inputStream ソケットから取得した入力ストリーム
     */
     public HTTPRequest(InputStream inputStream) {
        this.init(inputStream);
    }

    /**
     * HTTPRequestオブジェクトの初期値を設定するインスタンスメソッド。
     * @param inputStream 入力ストリーム
     */
     private void init(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("今から入力ストリーム経由でデータを取得します");
        try {
            String line = br.readLine();
            String[] requestLine = line.split(" ");
            this.requestMethod = requestLine[0];
            this.url = requestLine[1];

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
     * @return requestMethod
     */
    public String getRequestMethod() {
        return this.requestMethod;
    }

    /**
     * リクエストURIのゲッター
     * Webリソースの読み込みに必要なURIを返すインスタンスメソッド
     * @return requestURI
     */
    public String getRequestURI() throws Exception {
        String requestURI;
        if (this.url.contains("?")) {
            requestURI = this.url.substring(0, this.url.indexOf("?"));
        } else {
            requestURI = this.url;
        }
        requestURI = URLDecoder.decode(requestURI, "UTF-8");
        return requestURI;
    }

    /**
     * リクエストURIを分析してリソースまでの適切なパス名を返す
     * @return resourcePath
     */
    public String getResourcePath() {
        String resourcePath;
        String requestURI = null;
        // ここのtry-catchがわからん。
        try {
            requestURI = this.getRequestURI();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
        if (requestURI.endsWith("/")) {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/") + "index.html";
        } else {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/");
        }
        return resourcePath;
    }
}
