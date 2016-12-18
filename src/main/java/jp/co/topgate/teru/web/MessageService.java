package jp.co.topgate.teru.web;

/**
 * Created by e125761 on 2016/12/18.
 */
// Messageクラスを管理する
public class MessageService {
    private String[] messages;
    // コンストラクタ
    MessageService() {
        // 配列を作る
        String messages[] = {"hello", "hey", "hi"};

        // 作った配列をフィールドに設定する。
        this.messages = messages;
    }
    // レンダリング
    public String generate() {
        // ボディ部分を生成する。
        // HTMLのハードコーディング
        // 配列の中身を繰り返し表示する

        StringBuilder stringBuilder = new StringBuilder();
        for (String message: this.messages) {
            stringBuilder.append("<h1>" + message + "</h1>");
        }

        return stringBuilder.toString();

    }
}
