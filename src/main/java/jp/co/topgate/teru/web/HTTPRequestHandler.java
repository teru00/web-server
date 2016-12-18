package jp.co.topgate.teru.web;

import jp.co.topgate.teru.web.http.HTTPRequest;
import jp.co.topgate.teru.web.http.HTTPResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

                response.setStatusCode(200);
                response.setReasonPhrase("OK");
                response.setHeader("Content-Type", response.getContentType(file.getName()));
                response.setMessageBody(file);

            } else {
                System.out.println("リソースは存在しませんでした。");
                response.setStatusCode(404);
                response.setReasonPhrase("Not Found");
                response.setHeader("Content-Type", "text/html");
                response.setMessageBodyError(errorHandle("404"));
            }

        } else {
            System.out.println("許可されていないHTTPメソッドです。");
            response.setStatusCode(405);
            response.setReasonPhrase("Method not allowed Explained");
            response.setHeader("Content-Type", "text/html");
            response.setMessageBodyError(errorHandle("405"));
        }
        return response;
    }

    /**
     * エラーコンテンツを文字列で返すハンドラメソッド
     *
     * @param errorStatus エラーステータスコード
     * @return エラーコンテンツ
     */
    String errorHandle(String errorStatus) {
        Map<String, String> errorMessage = new HashMap<String, String>() {
            {
                put("404", "Not Found");
                put("405", "Method not allowed Explained");
            }
        };
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html>\n");
        stringBuilder.append("    <head>\n");
        stringBuilder.append("        <meta charset=\"UTF-8\" />\n");
        stringBuilder.append("        <title>Simple HTTP Server</title>\n");
        stringBuilder.append("    <body>\n");
        stringBuilder.append("        <h1>" + errorStatus + " " + errorMessage.get(errorStatus) + "</h1>\n");
        stringBuilder.append("    </body>\n");
        stringBuilder.append("</html>");

        String errorContent = new String(stringBuilder);
        return errorContent;
    }
}
