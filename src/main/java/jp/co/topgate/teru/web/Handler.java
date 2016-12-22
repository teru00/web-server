package jp.co.topgate.teru.web;

// 抽象クラス
public abstract class Handler {

    public HTTPResponse handleGet(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        return response;
    }
    public HTTPResponse handlePost(HTTPRequest request) {
        // POSTリクエストが必要ない場合でも、継承すると呼び出し可能になる。
        // NullPointerExceptionがどこかで発生するので、ここで、明確にErrorを発生させておく。
        // 本家Servletでは、 Versionを確認している。
        // そして、例外オブジェクトを発生させ、呼び出し元に投げている。
        // 呼び出し元がhandlePost()にディスパッチする処理を自動化していた場合、抽象クラスから継承したメソッド
        // が勝手に呼び出される場合があるのだ。
        // つまり、そういった異常系に対してどう対処するかというのがここでの定義の肝なのだ。
        HTTPResponse response = new HTTPResponse();
        return response;

    }
}

