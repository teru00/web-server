package jp.co.topgate.teru.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

public class HTTPRequest {
    private InputStream inputStream;

    /*
    フィールド
        リクエストライン
     */

    private String[] requestLine;

    public HTTPRequest(InputStream inputStream) {
        this.inputStream = inputStream;
        //リクエストメッセージの読み込み
        this.setRequestElements();
    }
    public void setRequestElements() {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
        //1行目を読み込む
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Method,URI,Versionに分ける
        this.requestLine = line.split(" ");
    }
    //リクエストメソッドを抽出
    public String getRequestMethod() {
        return this.requestLine[0];
    }

    //リクエストURIを抽出
    public String getRequestURI() {
        return this.requestLine[1];
    }
//    public String getQueryString() {}
//    private String getRequestLine() {}
}
