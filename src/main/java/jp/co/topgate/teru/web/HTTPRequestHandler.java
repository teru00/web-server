package jp.co.topgate.teru.web;

import java.io.*;

/**
 * リクエストオブジェクトを分析して、適切なレスポンスを組み立てるハンドラクラス。
 *
 */
class HTTPRequestHandler {

    /**
     * 受信したリクエストを分析して適切なレスポンスを組み立てるためのインスタンスメソッド。
     * リクエストURIに応じて、リソースファイルの読み込みを行う。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException
     */
    //リクエストの内容でレスポンスを組み立てる
    public HTTPResponse handle(HTTPRequest request) throws IOException {

        HTTPResponse response = new HTTPResponse();

        //GETかどうかを確認する
        if ("GET".equals(request.getRequestMethod())) {

            //抽象パス=をつくる
            //index.html固定なのがどうか
            File file;
            String pathname;
            //対応しているパターン
            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/")) {
                pathname = "src/main/resources" + requestURI.replaceAll("/+", "/") + "index.html";
            } else {
                pathname = "src/main/resources" + requestURI;
            }
            file = new File(pathname);

            //リソースの有無を確認する
            if (file.exists()) {
                System.out.println("リソースは存在しました。");
                //入力ストリームとメモリへの出力ストリーム
                InputStream inputStream = new FileInputStream(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                //出力ストリームの内部領域にデータを積んでいく
                int len; //なぜlenか？
                //バイトデータを取得し、バッファに書き込む。
                while ((len = inputStream.read()) != -1) {
                    byteArrayOutputStream.write(len);
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
                //バッファに全て格納したら、byte配列としてメモリを確保する。
                //コンテンツ
                byte[] byteContent = byteArrayOutputStream.toByteArray();

                //役目を終えたので、close
                inputStream.close();

                //200レスポンス要素を設定する
                response.setStatusLine("HTTP/1.1 200 OK");
                response.setHeader("Content-Type", response.getContentType(file.getName()));
                response.setMessageBody(byteContent);

            } else {
                response.setStatusLine("HTTP/1.1 404 Not Found");
                response.setHeader("Content-Type", "text/html");
                response.setMessageBody("404 Not Found".getBytes());
            }

        } else {
            response.setStatusLine("HTTP/1.1 405 Method not allowed Explained");
            response.setHeader("Content-Type", "text/html");
            response.setMessageBody("HTTP 405 Error Method not allowed Explained".getBytes());
        }
        return response;
    }
}
