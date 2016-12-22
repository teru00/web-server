package jp.co.topgate.teru.web;

/**
 * Created by terufumishimoji on 2016/12/20.
 */
public class ErrorTemplate extends Template {

    HTTPRequest request;
    HTTPResponse response;

    ErrorTemplate(HTTPRequest request, HTTPResponse response) {
        this.request = request;
        this.response = response;
    }

    public void generate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html>\n");
        stringBuilder.append("    <head>\n");
        stringBuilder.append("        <meta charset=\"UTF-8\" />\n");
        stringBuilder.append("        <title>Simple HTTP Server</title>\n");
        stringBuilder.append("    <body>\n");
        stringBuilder.append("        <h1>" + this.response.getStatusLine() + "</h1>\n");
        stringBuilder.append("    </body>\n");
        stringBuilder.append("</html>");

        String errorContent = new String(stringBuilder);
        response.setMessageBodyError(errorContent);
    }
}

/*
ステータスコードはint型にすべし

Templateの設計が微妙な気がする。
急がば回れなのだよ。
レスポンスボディのセッティングをラッパするべきかどうか。
 */