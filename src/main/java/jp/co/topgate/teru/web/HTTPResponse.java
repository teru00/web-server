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
    private Map<String, String> headersField = new HashMap<>();

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
     * @param name ヘッダー名
     * @param value 値
     */
    void setHeader(String name, String value) {
        //引数KVをMapのKVに設定する
        this.headersField.put(name, value);
    }

    /**
     * ヘッダーフィールドを追加するためのインスタンスメソッド
     * @param name ヘッダー名
     * @param value 値
     */
    public void addHeader(String name, String value) {
        if (this.headersField.containsKey(name)) {
            throw new RuntimeException("the mapping already exists");
        } else {
            this.headersField.put(name, value);
        }
    }


    /**
     * Mapで管理していたヘッダーフィールドの要素を書き出す
     * @return ヘッダーフィールドのStringデータ
     */
    String getHeadersField() {
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
     * @param messageBody レスポンスボディ
     */
    void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }



    /**
     * クライアントに送信するレスポンスメッセージを組み立てるインスタンスメソッド。
     * @return responseMessage
     */
    byte[] getResponseMessage() {
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
    String getContentType(String filename) {
        Map<String, String> contentTypeMap = new HashMap<String, String>() {
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
        String contentType;
        if (contentTypeMap.get(extension) != null) {
            contentType = contentTypeMap.get(extension);
        } else {
            contentType = "application/octet-stream";
        }
        return contentType;
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
    String getStatusLine() {
        return this.statusLine;
    }
}
