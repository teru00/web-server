package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
    //レスポンスの状態データ
    private String statusLine;
    private Map<String, String> headersField = new HashMap<String, String>();
    private byte[] messageBody;

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public void setHeader(String name, String value) {
        //引数KVをMapのKVに設定する
        this.headersField.put(name, value);
    }
    public void addHeader(String name, String value) {
        this.headersField.put(name, value);
    }
    //レスポンスヘッダーを文字列で取得する
    public String getheadersField() {
        StringBuilder buff = new StringBuilder();

        for(String key: this.headersField.keySet()) {
            buff.append(key);
            buff.append(": ");
            buff.append(this.headersField.get(key));
            buff.append("\n");
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

        byte[] responseHeader = (this.statusLine + "\n" + this.getheadersField() + CRLF).getBytes();
        byte[] responseMessage = new byte[responseHeader.length + this.messageBody.length];

        //ヘッダーの各要素をマージする
        System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
        System.arraycopy(this.messageBody, 0, responseMessage, responseHeader.length, this.messageBody.length);

        return responseMessage;
    }
}
