package jp.co.topgate.teru.web;

class DynamicContentHandler implements Handler {

    /**
     * GETメソッドが送信されてきた時の処理
     *
     * @param request
     * @return
     * @throws Exception
     */
    HTTPResponse doGET(HTTPRequest request) throws Exception {
        HTTPResponse response = new HTTPResponse();
        return response;
    }

    /**
     * POSTメソッドが送信されてきた時の処理
     *
     * @param request
     * @return
     */
    HTTPResponse doPOST(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        return response;
    }

}