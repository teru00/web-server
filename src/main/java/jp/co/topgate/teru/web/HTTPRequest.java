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
     * HTTPRequestオブジェクトの初期値を設定するインスタンスメソッド。
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
                // int contentLength;
                while(line != null  && !line.isEmpty()) {
                    line = br.readLine();
                    System.out.println(line);
                    if (line.startsWith("Content-Length")) {
                        String[] tmp = line.split(" ");
                        contentLengthString = tmp[1];
                        // contentLength = Integer.parseInt(contentLengthString);
                    }
                }
                System.out.println(line);
                line = br.readLine();
                System.out.println(line);
                // this.requestBody = line;
                //System.out.println(contentLengthString);
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

    String getRequestPamameter(String name) {
        // 外部から指定されたnameが存在しなかった場合の例外処理
        Map<String, String> map = new HashMap<>();
        String[] temp = requestBody.split("&");
        for (String set: temp) {
            String[] formData = set.split("=");
            map.put(formData[0], formData[1]);
        }
        String value = map.get(name);
        return value;
        // マッピング処理はどこかに分けたほうが見通しは良いかも。
        // 誰にとっての見通しかどうか。
        // privateメソッドを使えばええんちゃう？
    }
    String getRequestBody() {
        return this.requestBody;
    }
}
