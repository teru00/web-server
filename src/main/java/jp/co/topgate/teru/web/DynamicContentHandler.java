package jp.co.topgate.teru.web;

class DynamicContentHandler extends Handler {

    /**
     * GETメソッドが送信されてきた時の処理
     *
     * @param request HTTPリクエスト
     * @return HTTPレスポンス
     */
    @Override
    public void handleGet(HTTPRequest request, HTTPResponse response) {
    }

    /**
     * POSTメソッドが送信されてきた時の処理
     *
     * @param request HTTPリクエスト
     * @return HTTPレスポンス
     */
    @Override
    public void handlePost(HTTPRequest request) {
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