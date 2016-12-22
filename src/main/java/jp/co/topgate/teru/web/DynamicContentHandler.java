package jp.co.topgate.teru.web;

class DynamicContentHandler extends Handler {

    /**
     * GETメソッドが送信されてきた時の処理
     *
     * @param request HTTPリクエスト
     * @return HTTPレスポンス
     */
    @Override
    public HTTPResponse handleGet(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        return response;
    }

    /**
     * POSTメソッドが送信されてきた時の処理
     *
     * @param request HTTPリクエスト
     * @return HTTPレスポンス
     */
    @Override
    public HTTPResponse handlePost(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();

        return response;
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