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