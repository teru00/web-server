package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 *　クライアントに送信するHTTPレスポンスを生成するクラス。
 *
 */
public class HTTPResponse {

    /**
     * レスポンスステタースラインを表す。
     * e.g.) HTTP/1.1 200 OK
     */
    private String statusLine;

    /**
     * レスポンスヘッダーフィールドを表す。
     * e.g) Content Type: text/html
     */
    private Map<String, String> headersField = new HashMap<String, String>();

    /**
     * レスポンスボディを表す。
     */
    private byte[] messageBody;

    /**
     * ステータスラインのセッター
     * @param statusLine
     */
    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    /**
     * ヘッダーフィールドのセッター
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        //引数KVをMapのKVに設定する
        this.headersField.put(name, value);
    }

    /**
     * ヘッダーフィールドを追加するためのインスタンスメソッド
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        this.headersField.put(name, value);
    }


    /**
     * Mapで管理していたヘッダーフィールドの要素を書き出す
     * @return ヘッダーフィールドのStringデータ
     */
    public String getHeadersField() {
        StringBuilder buff = new StringBuilder();

        for(String key: this.headersField.keySet()) {
            buff.append(key);
            buff.append(": ");
            buff.append(this.headersField.get(key));
            buff.append("\n");
        }
        return buff.toString();
    }

    /**
     *
     * @param messageBody
     */
    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }



    /**
     * クライアントに送信するレスポンスメッセージを組み立てるインスタンスメソッド。
     * @return responseMessage
     */
    public byte[] getResponseMessage() {
        //レスポンスヘッダーとレスポンスボディの間の空白行
        final String CRLF = "\n";

        byte[] responseHeader = (this.statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
        byte[] responseMessage = new byte[responseHeader.length + this.messageBody.length];

        //ヘッダーの各要素をマージする
        System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
        System.arraycopy(this.messageBody, 0, responseMessage, responseHeader.length, this.messageBody.length);

        return responseMessage;
    }
}
