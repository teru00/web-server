package jp.co.topgate.teru.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by terufumishimoji on 2016/11/28.
 */
public class HTTPRequestHandler {

    public HTTPResponse handle(HTTPRequest request) {
        //GETかどうかを確認する
        HTTPResponse response = new HTTPResponse();

        if ("GET" === request.getRequestMethod()) {
            //リソースを取得する

            //抽象パスをつくる
            //index.html固定なのがどうか
            File file = new File("/src/main/resources/" + request.getRequestURI() + "index.html");

            //リソースがあるか確認する
            if (file.exists()) {
                //IO
                //レスポンスステータス決定
                //レスポンスヘッダー決定
                //レスポンスボディ生成
            } else {
                //Not Found
            }

        }
        return response;
    }
//    //requestMethod
//    private final String GET = "GET";
//    private final String POST = "POST";
//
//    public HTTPResponse handle(HTTPRequest request) {
//
//        if (GET == request.getRequestMethod()) {
//            //ファイルを読み込み
//            //リクエストURI => /
//            file = new File(request.getRequestURI);
//        } else {
//            //Sorry, I only know GET Method...
//        }
//
//        //読み込んだファイルによって、処理を変える。
//        //ここでレスポンスを作りたい。
//        HTTPResponse response = null;
//        response = new HTTPResponse();
//        //レスポンスにステータスライン（1行目）を設定
//        response.setStatusLine();
//        //レスポンスヘッダーを設定（2行目以降）
//        response.setResponseHeader();
//        //ファイルの中身を見てMIMEタイプを決定する。
//        response.setContentType();
//        response.setMessageBody();
//        //今回の仕様を満たすresponseを完成
//
//
//        return response;
//    }
}
