package jp.co.topgate.teru.web;

import java.io.File;

/**
 * Created by terufumishimoji on 2016/11/28.
 */
public class HTTPRequestHandler {

    public String handle(HTTPRequest request) {
        String message = "file not Found";
        //requestURIを取得してFileを探す
        String uri = request.getRequestURI();
        File file = new File(uri);
        if (file.exists()) {
            message = "success";
            //Fileの読み込みをする
            //Fileの読み込みが成功
            //StatusCodeの決定
            //Content-typeの決定
        } else {
            //Fileがない場合
            //StatusCodeが決定
            //404のテキストデータを返す

        }
        return message;
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
