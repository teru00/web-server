package jp.co.topgate.teru.web;

import java.io.IOException;

class DynamicContentHandler extends Handler {

    /**
     * GETメソッドが送信されてきた時の処理
     * @param request HTTPリクエスト
     */
    @Override
    public void handleGet(HTTPRequest request, HTTPResponse response) {
        System.out.println("動的ファイルは存在しました。");

        response.setStatusCode(200);
        response.setReasonPhrase("OK");
        response.setHeader("Content-Type", "text/html");

        // ビルダー
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<html lang=\"jp\">");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>The HTML5 Herald</title>");
        stringBuilder.append("<meta name=\"description\" content=\"The HTML5 Herald\">");
        stringBuilder.append("<meta name=\"author\" content=\"SitePoint\">");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");
        stringBuilder.append("<form method=\"post\">");
        stringBuilder.append("<input name=\"say\" value=\"Hi\">");
        stringBuilder.append("<input name=\"to\" value=\"Mom\">");
        stringBuilder.append("<button>Send my greetings</button>");
        stringBuilder.append("</form>");
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");

        String content = stringBuilder.toString();
        response.setMessageBodyError(content);
        try {
            response.respond();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POSTメソッドが送信されてきた時の処理
     * @param request HTTPリクエスト
     */
    @Override
    public void handlePost(HTTPRequest request, HTTPResponse response) {
        /**
         * requestをローディングする。
         * ボディを読み込み、状態保持のための変数を用意してあげる。
         * name="hogehoge", message="foobar"
         * インメモリにデータを書き込む。
         * インメモリからデータを読み込み、
         * テンプレート(HTML)を作成する。
         * Note: POSTメソッドはHTMLのform要素を利用してデータを
         * サーバに送信する役目を持つのはわかった。さらには、レスポンスを
         * 適当に返しておくれと伝える役目もあるそうだが、それについての詳しい情報がないだろうか。
         * GETとPOSTの返すレスポンスの組み立ての違いはなんだろうか？
         * 組み立てがシュール
         * Templateオブジェクトにレスポンスの文脈を持ち込んで、
         */
    }
}

/*
継承したクラスが誤って、オーバーライドせずにメソッドを実行する可能性はある。
そもそも、特定のサブクラスにおいて、本来の継承しても意味のないメソッドを継承しないといけない。

この異常系に対してどう対処するか。
呼び出された場合、エラーテンプレートを返そう。
ユーザーにアクセスエラーを出すということだ。（レスポンスを組み立てるということ）

SCとRP、エラーテンプレートを設定してレスポンスを組み立てる。
 */