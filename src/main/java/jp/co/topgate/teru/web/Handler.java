package jp.co.topgate.teru.web;

interface Handler {
    void doGET(HTTPRequest request);
    void doPOST(HTTPRequest request);
    // 戻り値は必要かどうか
    //
}