package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
    //レスポンスの状態データ
    private String statusLine;
    private Map<String, String> responseHeaders = new HashMap<String, String>();
    private byte[] messageBody;

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public void setResponseHeader(String name, String value) {
        //引数KVをMapのKVに設定する
        this.responseHeaders.put(name, value);
    }
    public void addResponseHeader(String name, String value) {
        this.responseHeaders.put(name, value);
    }
    //レスポンスヘッダーを文字列で取得する
    public String getResponseHeaders() {
        final String CRLF = "\n";
        StringBuilder buff = new StringBuilder();

        for(String key: this.responseHeaders.keySet()) {
            buff.append(key);
            buff.append(": ");
            buff.append(this.responseHeaders.get(key));
            buff.append(CRLF);
        }

        return buff.toString();
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    //レスポンスメッセージをソケットに送信するために準備する
    public byte[] getResponseMessage() {
        //レスポンスヘッダーとレスポンスボディの間の空白行
        final String CRLF = "\n";

        byte[] responseHeader = (this.statusLine + "\n" + this.getResponseHeaders() + "\n" + CRLF).getBytes();
        byte[] responseMessage = new byte[responseHeader.length + this.messageBody.length];

        //ヘッダーの各要素をマージする
        System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
        System.arraycopy(this.messageBody, 0, responseMessage, responseHeader.length, this.messageBody.length);

        return responseMessage;
    }

//        //レスポンスメッセージをバイトデータで返す
//        Byte[] statusLine = this.statusLine.getBytes();
//        Byte[] responseHeaders = this.getResponseHeaders().getBytes();
//        Byte[] messageBody = this.messageBody;
//        return this.mergeResponseElements(statusLine, responseHeaders, messageBody);
//    }

    //(R)ステータスラインーとレスポンスヘッダーをマージする
//    private String getResponseHeader() {}

    //バイト配列マージ専用処理
//    private Byte[] mergeResponseElements(Byte[] statusLine, Byte[] responseHeaders, Byte[] messageBody) {
//        //レスポンスの構成要素をマージする
//        Byte[] crlf;
//        Byte[] responseMessage = new byte[statusLine.length + responseHeaders.length + crlf.length + messageBody.length];
//        //マージする
//        System.arraycopy(statusLine,0,responseMessage,0,statusLine.length);
//        System.arraycopy(responseHeaders,0,responseMessage,statusLine.length, responseHeaders.length);
//        System.arraycopy(crlf,0,responseMessage,responseHeaders.length, crlf.length);
//        System.arraycopy(messageBody,0,responseMessage,crlf.length, messageBody.length);
//        return responseMessage;
//    }
}
