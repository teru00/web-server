package jp.co.topgate.teru.web;

// 抽象クラス
public abstract class Handler {

    /**
     * GETリクエストを解析してレスポンスを組み立てるハンドラメソッド
     * オーバーライドされることを意図した実装
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    public void handleGet(HTTPRequest request, HTTPResponse response) {
        response.setStatusCode(405);
        response.setReasonPhrase("Method Not Allowed");
        ErrorTemplate errorTemplate = new ErrorTemplate(response);
        errorTemplate.generate();
        //response.respond();
        // ここでオブジェクトの型をTemplateにする意味はあるのか
    }

    /**
     * POSTリクエストを解析してレスポンスを組み立てるハンドラメソッド
     * オーバーライドされることを意図した実装
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     */
    public void handlePost(HTTPRequest request, HTTPResponse response) {
        // POSTリクエストが必要ない場合でも、継承すると呼び出し可能になる。
        // NullPointerExceptionがどこかで発生するので、ここで、明確にErrorを発生させておく。
        // 本家Servletでは、 Versionを確認している。
        // そして、例外オブジェクトを発生させ、呼び出し元に投げている。
        // 呼び出し元がhandlePost()にディスパッチする処理を自動化していた場合、抽象クラスから継承したメソッド
        // が勝手に呼び出される場合があるのだ。
        // つまり、そういった異常系に対してどう対処するかというのがここでの定義の肝なのだ。
        response.setStatusCode(405);
        response.setReasonPhrase("Method Not Allowed");
        ErrorTemplate errorTemplate = new ErrorTemplate(response);
        // responseボディのセッティングが内包されている分可読性が低いような気がする。
        errorTemplate.generate();
        //response.respond();
    }
}
/*
requestが利用されていないアンチパターンに対してどう対応するか。（一応クエリパラメータを使う場合もある）
responseがここで呼び出されることで例外処理を行わないといけない。
throwsしちゃおうかな,,,
 */
