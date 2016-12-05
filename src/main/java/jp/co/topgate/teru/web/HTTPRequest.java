package jp.co.topgate.teru.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author terufumi shimoji
 *
 * HTTPリクエストを表現するクラス
 */
public class HTTPRequest {

    //private InputStream inputStream;

    /*
    HTTPリクエストの1行目
    最低限のWebサーバはこのリクエストラインを解析できればよい。
    */
    private String[] requestLine;

    public HTTPRequest(InputStream inputStream) {
        //this.inputStream = inputStream;
        //リクエストメッセージの読み込み
        this.init(inputStream);
    }
    public void init(InputStream inputStream) {
        //クライアントソケットに関連づいた入力ストリームをバッファでラッパ
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("今から入力ストリーム経由でデータを取得します");
        //1行目を読み込む
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
        //リクエストラインを分割
        this.requestLine = line.split(" ");
    }
    //リクエストメソッドを抽出
    public String getRequestMethod() {
        return this.requestLine[0];
    }

    //リクエストURIを抽出
    public String getRequestURI() {
        //クエリパラメータ削除
        String requestURI;
        if (requestLine[1].contains("?")) {
            requestURI = this.requestLine[1].substring(0, requestLine[1].indexOf("?"));
        } else {
            requestURI = this.requestLine[1];
        }
        return requestURI;
    }
}
