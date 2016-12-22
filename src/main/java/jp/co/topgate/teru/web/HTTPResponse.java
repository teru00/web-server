package jp.co.topgate.teru.web;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
    private OutputStream outputStream;
    private int statusCode;
    // setter
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private String reasonPhrase;
    // setter
    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    // コンストラクタ
    HTTPResponse(OutputStream outputstream) {
        this.outputStream = outputstream;
    }

    // handleの中でsetされている
    // statusCodeなど新しい状態を持たせるやり方
    // メソッドを新しく定義するやり方
    // statusCodeとreasonPhraseとVersionさえあれば事足りる
    // 一番簡単なy利方としては、新しい状態を持たせること
    // 何れにしても状態を持たせないといけないのでは、handle内での
    // 状態とmainでの状態は時間的な流れがあり、状態遷移が起きる
    // オブジェクトに値を保持してもらわないと困るのだ。それかrespondに
    // 状態を持たせるやり方がある。handleの中でやっているのは、状態の決定
    // requestの状態をパースすることで、responseの状態を決定している。
    // mainの中でやっているのはある状態をもったresponseオブジェクトに
    // 送信命令をすること。
    // 送信命令を受けた、responseはみづからが保持する状態を使って
    // レスポンス全体を構成する。
    // レスポンスに新しく状態を持たせるとしたら、

    /**
     * レスポンスヘッダーフィールドを表す。
     * e.g) Content Type: text/html
     */
    private Map<String, String> headersField = new HashMap<>();

    /**
     * エラーコンテンツを保持するレスポンスボディ
     */
    private String messageBodyError;

    /**
     * クライアントに送信する静的ファイルへのファイルパスを保持するレスポンスボディ
     */
    private File messageBody;

    /**
     * ヘッダーフィールドのセッター
     * @param name ヘッダー名
     * @param value 値
     */
    void setHeader(String name, String value) {
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
     * エラーコンテンツを保持するフィールド
     *
     * @param messageBodyError エラーコンテンツ
     */
    void setMessageBodyError(String messageBodyError) {
        this.messageBodyError = messageBodyError;
    }

    /**
     * クライアントに送信するリソースを保持するフィールド
     *
     * @param file 静的ファイル
     */
    void setMessageBody(File file) {
        this.messageBody = file;
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

    /**
     * テスト用のメソッド
     */
    String getStatusLine() {
        return this.statusLine;
    }

    /**
     * HTTPレスポンスをクライアントに送信する処理
     *
     * @throws IOException IO系の例外
     */
    public void respond() throws IOException {
        final String CRLF = "\r\n";
        final String httpVersion = "HTTP/1.1";
        String statusCodeString = String.valueOf(this.statusCode);
        String statusLine = httpVersion + " " + statusCodeString +  " " + this.reasonPhrase;

        if (this.messageBody != null) {
            DataSource dataSource = new FileDataSource(this.messageBody);
            DataHandler dataHandler = new DataHandler(dataSource);
            byte[] responseHeader = (statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
            this.outputStream.write(responseHeader, 0, responseHeader.length);
            dataHandler.writeTo(this.outputStream);
            this.outputStream.flush();
            this.outputStream.close();
        } else {
            byte[] responseHeader = (statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
            byte[] responseBody = this.messageBodyError.getBytes();
            byte[] responseMessage = new byte[responseHeader.length + responseBody.length];
            System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
            System.arraycopy(responseBody, 0, responseMessage, responseHeader.length, responseBody.length);
            this.outputStream.write(responseMessage, 0, responseMessage.length);
            this.outputStream.flush();
            this.outputStream.close();
        }
    }

}
