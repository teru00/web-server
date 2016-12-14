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
    private File messageBodyFile;

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
    void setMessageBodyFile(File file) {
        this.messageBodyFile = file;
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

    /**
     * HTTPレスポンスをクライアントに送信する処理
     *
     * @param outputStream 出力先ストリーム
     * @throws IOException IO系の例外
     */
    public void respond(OutputStream outputStream) throws IOException {
        final String CRLF = "\r\n";

        if (this.messageBodyFile != null) {
            // Filedatasource紐付け
            DataSource dataSource = new FileDataSource(this.messageBodyFile);
            // datahandlerを生成
            DataHandler dataHandler = new DataHandler(dataSource);
            //レスポンスヘッダーをarraycopyでマージする
            byte[] responseHeader = (this.statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
            // 先にレスポンスヘッダーを送信する
            outputStream.write(responseHeader, 0, responseHeader.length);
            // そのあとでレスポンスボディーがもっているfileクラスを使ってデータを送信する。
            dataHandler.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } else {
            byte[] responseHeader = (this.statusLine + "\n" + this.getHeadersField() + CRLF).getBytes();
            byte[] responseBody = this.messageBodyError.getBytes();
            byte[] responseMessage = new byte[responseHeader.length + responseBody.length];

            System.arraycopy(responseHeader, 0, responseMessage, 0, responseHeader.length);
            System.arraycopy(responseBody, 0, responseMessage, responseHeader.length, responseBody.length);

            outputStream.write(responseMessage, 0, responseMessage.length);
            outputStream.flush();
            outputStream.close();
        }
    }

}
