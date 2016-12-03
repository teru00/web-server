package jp.co.topgate.teru.web;

import java.io.*;

class HTTPRequestHandler {

    //リクエストの内容でレスポンスを組み立てる
    HTTPResponse handle(HTTPRequest request) throws IOException {

        HTTPResponse response = new HTTPResponse();

        //GETかどうかを確認する
        if ("GET".equals(request.getRequestMethod())) {

            //抽象パス=をつくる
            //index.html固定なのがどうか
            File file = new File("/src/main/resources/" + request.getRequestURI() + "index.html");

            //リソースがあるか確認する
            if (file.exists()) {
                //入力ストリームとメモリへの出力ストリーム
                InputStream is = new FileInputStream(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                //出力ストリームの内部領域にデータを積んでいく
                int len; //なぜlenか？
                while ((len = is.read()) != -1) {
                    byteArrayOutputStream.write(len);
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
                //バッファに全て格納したら、byte配列としてメモリを確保する。
                //コンテンツ
                byte[] byteContent = byteArrayOutputStream.toByteArray();

                String statusLine = "HTTP/1.1 200 OK";
                String headers = "Content-Type: text/html";
                response.setMessageBody(byteContent);

            } else {
                //Not Found
                System.out.println("404 Not Found");
            }

        } else {
            //405
            //許可されていないHTTPメソッド
            System.out.println("405");
        }
        return response;
    }
}
