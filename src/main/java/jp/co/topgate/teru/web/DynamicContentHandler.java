package jp.co.topgate.teru.web;


// 動的アプリケーションサーバ
// リクエストの内容に応じてレスポンスを組み立てる
// POST/GETメソッドが送信されてくる。
// このクラスが役割をはたすのに依存する状態は
// 主に、リクエストボディ

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

        //URI pattern => application new


        if (request.getRequestURI().equals("/program/board")) {
            MessageService messageService = new MessageService();
            // レスポンスを組み立て
            response.setStatusCode(200);
            response.setReasonPhrase("OK");
            response.setHeader("ContentType", "text/html");
            // Template template = new Template(MessageSerive);
            // template.generate();
            response.setMessageBodyError(messageService.generate());
        }
        return response;
    }

    /**
     * POSTメソッドが送信されてきた時の処理
     *
     * @param request
     * @return
     */
    HTTPResponse doPOST(HTTPRequest request) {
        // ここでもどのアプリケーションか判断することになる
        // 冗長である
        HTTPResponse response = new HTTPResponse();
        return response;
    }

    // searchの処理はどうするか。
    // 依存はクエリパラメータ
    // GETリクエストとクエリパラメータに依存しているのでそのりくえすとをそうしんされてきたことを補足する
    //

}