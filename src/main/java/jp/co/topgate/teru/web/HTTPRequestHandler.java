package jp.co.topgate.teru.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

            String requestURI = request.getRequestURI();
            if (requestURI.equals("/")) {
                pathname = "src/main/resources" + requestURI + "index.html";
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
                response.setHeader("Content-Type", getContentType(file.getName()));
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

    /**
     * 指定されたwebリソースの拡張子を抽出して、Content Typeを決定する。
     * このContent Typeはブラウザが読み込んだリソースを認識するのに役立つ。
     *
     * @param filename Content Typeを決定するために必要なリソースファイル名
     * @return なし
     */
    public String getContentType(String filename) {
        //ファイル名から拡張子を抽出する
        Map<String, String> contentType = new HashMap<String, String>() {
            {
                //リクエストの度に毎回読み込んでるからメモリ浪費するな。(改善点)
                put("html", "text/html");
                put("css", "text/css");
                put("jpeg", "image/jpeg");
                put("png", "image/png");
                put("gif", "image/gif");
                put("js", "application/javascript");
                //後幾つかあるよ。
            }
        };
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        //拡張子をKとして対応するVを返してあげる。
        return contentType.get(extension);
    }

}
