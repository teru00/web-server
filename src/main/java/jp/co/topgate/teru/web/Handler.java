package jp.co.topgate.teru.web;

interface Handler {
    void doGET(HTTPRequest request);
    void doPOST(HTTPRequest request);
}