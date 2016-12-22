package jp.co.topgate.teru.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 静的ファイルサーバ
 *
 * Created by terufumishimoji on 2016/12/16.
 */
public class StaticContentHandler extends Handler {
    /**
     * リクエストを解析して適切なレスポンスを組み立てる。
     * @param request HTTPリクエスト
     * @return HTTPレスポンス
     */
    @Override
    public HTTPResponse handleGet(HTTPRequest request) {

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
                // 404ハンドリング
                ErrorTemplate errorTemplate = new ErrorTemplate();
                errorTemplate.errorHandle("404");
                // レスポンスボディにセッティングするまでの責務を委譲するかの判断も可能になる。
                // その場合はresponseが依存するので、状態を持つ必要がある。
                // 例えば、errorHandle(statusCode, response);みたいな。

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
        final Map<String, String> errorMessage = new HashMap<String, String>() {
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
