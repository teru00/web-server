package jp.co.topgate.teru.web;

import java.io.*;
import jp.co.topgate.teru.web.HTTPResponse;

/**
 * Created by terufumishimoji on 2016/11/28.
 */
public class HTTPRequestHandler {

    //リクエストの内容でレスポンスを組み立てる
    public HTTPResponse handle(HTTPRequest request) {

        //GETかどうかを確認する
        if ("GET" === request.getRequestMethod()) {

            //抽象パスをつくる
            //index.html固定なのがどうか
            File file = new File("/src/main/resources/" + request.getRequestURI() + "index.html");

            //リソースがあるか確認する
            if (file.exists()) {
                byte[] byteContents = null;
                //データソースをバイトで取得するストリームを生成
                InputStream is = new FileInputStream(file);
                //バイト配列にデータを流し込んでくれるストリームを生成
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                //バイトをバッファにためて、byte配列に書き込む。
                int len;
                while ((len = is.read()) != -1) {
                    bao.write(len);
                }
                //バイトデータが残っている場合強制的にバッファに書き込む。
                if (bao != null) {
                    bao.flush();
                    bao.close();
                }
                //メモリを割り当てて、バイト配列を生成する
                byteContents = bao.toByteArray();

                //レスポンス組み立て
                HTTPResponse response = new HTTPResponse();
                response.setStatusLine("HTTP/1.1 200 OK");
                response.setResponseHeader("Content-Type", "text/html");
                response.setMessageBody(byteContents);
            } else {
                //Not Found
            }

        } else {
            //405
            //許可されていないHTTPメソッド
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
