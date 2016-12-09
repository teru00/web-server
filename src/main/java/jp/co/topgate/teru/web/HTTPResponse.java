package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *　クライアントに送信するHTTPレスポンスを生成するクラス。
 */
class HTTPResponse {

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
     * @param statusCode ステータスコード
     */
    void setStatusLine(String statusCode) {
        final String httpVersion = "HTTP/1.1";
        String reasonPhrase = getReasonPhrase(statusCode);
        this.statusLine = httpVersion + " " + statusCode + " " + reasonPhrase;
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
a     * ヘッダーフィールドを追加するためのインスタンスメソッド
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
        final String CRLF = "\r\n";
        StringBuilder buff = new StringBuilder();

        for(String key: this.headersField.keySet()) {
            buff.append(key);
            buff.append(": ");
            buff.append(this.headersField.get(key));
            buff.append(CRLF);
        }
        return buff.toString();
    }

    /**
     *
     * @param messageBody
     */
    public <T> void setMessageBody(T messageBody) {
        //if (messageBody.instanceof() != byte[])
        this.messageBody = messageBody;
    }



    /**
     * クライアントに送信するレスポンスメッセージを組み立てるインスタンスメソッド。
     * @return responseMessage
     */
    public byte[] getResponseMessage() {
        final String CRLF = "\r\n";

        byte[] responseHeader = (this.statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
        byte[] responseMessage = new byte[responseHeader.length + this.messageBody.length];

        System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
        System.arraycopy(this.messageBody, 0, responseMessage, responseHeader.length, this.messageBody.length);

        return responseMessage;
    }

    /**
     * 指定されたwebリソースの拡張子を抽出して、Content Typeを決定する。
     * このContent Typeはブラウザが読み込んだリソースを認識するのに役立つ。
     *
     * @param filename Content Typeを決定するために必要なリソースファイル名
     * @return なし
     */
    public String getContentType(String filename) {
        Map<String, String> contentType = new HashMap<String, String>() {
            {
                put("html", "text/html");
                put("css", "text/css");
                put("jpeg", "image/jpeg");
                put("png", "image/png");
                put("gif", "image/gif");
                put("js", "application/javascript");
                //後幾つかあるよ。
            }
        };
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        return contentType.get(extension);
    }
    private String getReasonPhrase(String statusCode) {
        Map<String, String> reasonPhrase = new HashMap<String, String>() {
            {
                put("200", "OK");
                put("404", "Not Found");
                put("405", "Method not allowed Explained");
            }
        };
        return reasonPhrase.get(statusCode);
    }
    /**
     * テスト用のメソッド
     */
    public String getStatusLine() {
        return this.statusLine;
    }
}
