//package jp.co.topgate.teru.web;
//
///**
// * Created by terufumishimoji on 2016/12/16.
// */
//public class Template {
//
//    /**
//     * コンストラクタ
//     */
//    public void Template() {}
//
//    public byte[] generateTemplate() {
//
//    }
//
//
//    // エラーテンプレートが依存している状態は何か、、、
//    // ステータスコード
//    /**
//     *
//     * @return
//     */
//    public byte[] generateErrorTemplate(int statusCode, String reasonPhrase) {
//        // ステータスコードに応じて
//        // コンテントを変更する。
//        // 静的ファイルサーバは結局HTML形式データを送信している。
//        // それに対してこちらもハードコーディングしてHTML形式データを
//        // 組み立ててBodyに設定してあげれば良いのだ。
//        String statusCodeString = String.valueOf(statusCode);
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("<p>" + statusCodeString + " " + reasonPhrase + "</p>");
//
//        byte[] errorContent = stringBuilder.toString().getBytes();
//        return errorContent;
//    }
//
//}
