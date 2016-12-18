package jp.co.topgate.teru.web;


// 動的アプリケーションサーバ
// リクエストの内容に応じてレスポンスを組み立てる
// POST/GETメソッドが送信されてくる。
// このクラスが役割をはたすのに依存する状態は
// 主に、リクエストボディ

class DynamicContentHandler {

    HTTPResponse doGET(HTTPRequest request) throws Exception {
        HTTPResponse response = new HTTPResponse();
        if (request.getRequestURI().equals("/program/board")) {
            MessageService messageService = new MessageService();
            // レスポンスを組み立て
            response.setStatusCode(200);
            response.setReasonPhrase("OK");
            response.setHeader("ContentType", "text/html");
            response.setMessageBodyError(messageService.generate());
        }
        return response;
    }

    HTTPResponse doPOST(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        return response;
    }


}