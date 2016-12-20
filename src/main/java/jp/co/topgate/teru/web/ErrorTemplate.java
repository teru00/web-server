package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terufumishimoji on 2016/12/20.
 */
public class ErrorTemplate {

    // このメソッドは副作用のないコード
    // 振る舞いが引数以外の状態に依存していない。

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
