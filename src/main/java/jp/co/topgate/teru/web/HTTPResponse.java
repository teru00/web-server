package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terufumishimoji on 2016/11/28.
 */
public class HTTPResponse {
    //レスポンスの状態データ
    private String statusLine;
    private Map<String, String> responseHeaders = new HashMap<String, String>();
    private Byte[] messageBody;

    //コンストラクタ


    //振る舞い
    //ステータスラインのsetter,getter
    public setStatusLine(String statusLine) {}
    public String getStatusLine() {}

    //レスポンスヘッダーのsetter,add,getter
    public setResponseHeaders(String name, String value) {}
    public addResponseHeader(String name, String value) {}
    public String getResponseHeaders() {}

    //レスポンスボディのsetter,getter
    public setMessageBody(Byte[] messageBody) {}
    public Byte[] getMessageBody() {}

    //レスポンスメッセージをソケットに送信するために準備する
    public Byte[] getResponseMessage() {
        //レスポンスメッセージをバイトデータで返す
        Byte[] statusLine = this.statusLine.getBytes();
        Byte[] responseHeaders = this.getResponseHeaders().getBytes();
        Byte[] messageBody = this.messageBody;
        return this.mergeResponseElements(statusLine, responseHeaders, messageBody);
    }

    //バイト配列マージ専用処理
    private Byte[] mergeResponseElements(Byte[] statusLine, Byte[] responseHeaders, Byte[] messageBody) {
        //レスポンスの構成要素をマージする
        Byte[] crlf;
        Byte[] responseMessage = new byte[statusLine.length + responseHeaders.length + crlf.length + messageBody.length];
        //マージする
        System.arraycopy(statusLine,0,responseMessage,0,statusLine.length);
        System.arraycopy(responseHeaders,0,responseMessage,statusLine.length, responseHeaders.length);
        System.arraycopy(crlf,0,responseMessage,responseHeaders.length, crlf.length);
        System.arraycopy(messageBody,0,responseMessage,crlf.length, messageBody.length);
        return responseMessage;
    }
}
