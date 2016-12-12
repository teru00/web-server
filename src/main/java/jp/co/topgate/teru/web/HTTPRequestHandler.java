package jp.co.topgate.teru.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * リクエストオブジェクトを分析して、適切なレスポンスを組み立てるハンドラクラス。
 */
class HTTPRequestHandler {

    /**
     *
     * 受信したリクエストを分析して適切なレスポンスを組み立てるためのインスタンスメソッド。
     * リクエストURIに応じて、リソースファイルの読み込みを行う。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException 入出力の例外
     */
    HTTPResponse handle(HTTPRequest request) throws Exception {

        HTTPResponse response = new HTTPResponse();

        if ("GET".equals(request.getRequestMethod())) {

            File file = new File(request.getResourcePath());

            if (file.exists() && file.isFile()) {
                System.out.println("リソースは存在しました。");

                // fileをロードするメソッド
                byte [] byteContent = readFile(file);

                response.setStatusLine("200");
                response.setHeader("Content-Type", response.getContentType(file.getName()));
                response.setMessageBody(byteContent);

            } else {
                System.out.println("リソースは存在しませんでした。");
                response.setStatusLine("404");
                response.setHeader("Content-Type", "text/html");
                response.setMessageBody("<h1>404 Not Found</h1>".getBytes());
            }

        } else {
            System.out.println("許可されていないHTTPメソッドです。");
            response.setStatusLine("405");
            response.setHeader("Content-Type", "text/html");
            response.setMessageBody("<h1>HTTP 405 Error Method not allowed Explained</h1>".getBytes());
        }
        return response;
    }

    private byte[] readFile(File file) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        while ((len = inputStream.read()) != -1) {
            byteArrayOutputStream.write(len);
        }
        if (byteArrayOutputStream != null) {
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
