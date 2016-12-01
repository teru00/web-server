package jp.co.topgate.teru.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;


public class HTTPRequest {
    private InputStream inputStream;

    HTTPRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    String getRequestMethod() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
            String requestLine = br.readLine();
            //requestLineから次のSPまで読み込む
            //subString(int beginIndex, int lastIndex)を利用して、必要なtokenを抽出する
            //beginIndexは0で、
            String requestMethod = requestLine.substring(0, requestLine.indexOf(" "));
            return requestMethod;
        } catch (IOException e) {
            //保留
            System.err.println(e);
            String str = "error";
            return str;
        }
    }

    String getRequestURI() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
            String requestLine = br.readLine();
            //リファクタリング必須。
            //スペースのマッチ回数を指定できるようにする。
            int startURI = requestLine.indexOf(" ") + 1; //空白以降のindexを返す。
            int endURI = requestLine.indexOf(" ", startURI); //2回目の空白以降から対象文字の一個前のインデックスを検索し返す。
            String requestURI = requestLine.substring(startURI, endURI);
            return requestURI;
        } catch (IOException e) {
            //保留
            System.err.println(e);
            String str = "error";
            return str;
        }
    }
}
