package jp.co.topgate.teru.web;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;

/**
 * 静的ファイルサーバ
 * Created by terufumishimoji on 2016/12/16.
 */
class StaticContentHandler extends Handler {
    /**
     * リクエストを解析して適切なレスポンスを組み立てる。
     * @param request HTTPリクエスト
     */
    @Override
    public void handleGet(HTTPRequest request, HTTPResponse response) throws IOException {

        if ("GET".equals(request.getRequestMethod())) {

            File file = new File(request.getResourcePath());

            if (file.exists() && file.isFile()) {
                System.out.println("リソースは存在しました。");

                response.setStatusCode(200);
                response.setReasonPhrase("OK");
                response.setHeader("Content-Type", response.getContentType(file.getName()));
                response.setStaticBody(file);
                response.respond();
            } else {
                System.out.println("リソースは存在しませんでした。");
                response.setStatusCode(404);
                response.setReasonPhrase("Not Found");
                response.setHeader("Content-Type", "text/html");
                ErrorTemplate errorTemplate = new ErrorTemplate(response);
                errorTemplate.generate();
                response.respond();
            }

        } else {
            System.out.println("許可されていないHTTPメソッドです。");
            response.setStatusCode(405);
            response.setReasonPhrase("Method not allowed Explained");
            response.setHeader("Content-Type", "text/html");
            ErrorTemplate errorTemplate = new ErrorTemplate(response);
            errorTemplate.generate();
            response.respond();
        }
    }
}
