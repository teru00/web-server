package jp.co.topgate.teru.web;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * クライアントから取得したHTTPリクエストの状態や
 * レスポンスを送信するための振る舞いを持つクラス。
 */
class HTTPRequest {
    
    private String requestMethod;
    private String url;
    private String requestBody;

    /**
     * inputStreamを使ってHTTPRequestオブジェクトに初期値を設定する。
     * @param inputStream ソケットから取得した入力ストリーム
     */
     HTTPRequest(InputStream inputStream) {
        this.init(inputStream);
    }

    /**
     * HTTPRequestの初期化処理を行うメソッド。
     * @param inputStream 入力ストリーム
     */
    private void init(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("今から入力ストリーム経由でデータを取得します");
        try {
            String line = br.readLine();
            String[] requestLine = line.split(" ");
            this.requestMethod = requestLine[0];
            this.url = requestLine[1];
            if (this.requestMethod.equals("POST")) {
                String contentLengthString = "";
                int contentLength = 0;
                while(line != null  && !line.isEmpty()) {
                    line = br.readLine();
                    System.out.println(line);

                    if (line.startsWith("Content-Length")) {
                        String[] tmp = line.split(" ");
                        contentLengthString = tmp[1];
                        contentLength = Integer.parseInt(contentLengthString);
                    }
                }
                // content-lengthに何かしらの値が入っている場合（何かしらではなく限定したい）
                if (contentLength != 0) {
                    char[] cbuf = new char[contentLength];
                    br.read(cbuf);
                    String content = new String(cbuf);
                    this.requestBody = content;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: "+ e);
            e.getStackTrace();
        }
    }

    /**
     *
     * リクエストメソッドのゲッター
     * 現時点ではGETかその他の判定をする
     * @return requestMethod
     */
    String getRequestMethod() {
        return this.requestMethod;
    }

    /**
     * リクエストURIのゲッター
     * Webリソースの読み込みに必要なURIを返すインスタンスメソッド
     * @return requestURI
     */
    String getRequestURI() throws Exception {
        String requestURI;
        if (this.url.contains("?")) {
            requestURI = this.url.substring(0, this.url.indexOf("?"));
        } else {
            requestURI = this.url;
        }
        requestURI = URLDecoder.decode(requestURI, "UTF-8");
        return requestURI;
    }

    /**
     * リクエストURIを分析してリソースまでの適切なパス名を返す
     * @return resourcePath
     */
    String getResourcePath() {
        String resourcePath;
        String requestURI = null;
        // ここのtry-catchがわからん。
        try {
            requestURI = this.getRequestURI();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
        if (requestURI.endsWith("/")) {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/") + "index.html";
        } else {
            resourcePath = "src/main/resources" + requestURI.replaceAll("/+", "/");
        }
        return resourcePath;
    }

    /**
     * リクエストボディのパラメータ名と値をマップで保持、取得可能にしているメソッド。
     * パラメータ名を引数に渡すと値を返す。
     * @param name
     * @return
     */
    String getRequestParameter(String name) {
        Map<String, String> requestParameter = new HashMap<>();
        String[] params = requestBody.split("&");
        for (String param : params) {
            String[] tmp = param.split("=");
            requestParameter.put(tmp[0], tmp[1]);
        }
        return requestParameter.get(name);
    }
    String getRequestBody() {
        return this.requestBody;
    }

    /**
     * テスト用のメソッド
     * @param requestBody リクエスボディの内容
     */
    void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
