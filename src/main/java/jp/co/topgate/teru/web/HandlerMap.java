package jp.co.topgate.teru.web;

import java.util.HashMap;
import java.util.Map;

/**
 * オブジェクトの責務
 * URLに送信するコンテンツハンドラをマッピングする責務を持つクラス。
 * マッピングした状態を保持する責務を持つクラス。
 * リクエストURIに応じてマッピングされたコンテンツハンドラを与える責務を持つクラス。
 *
 * Created by terufumishimoji on 2016/12/19.
 */
public class HandlerMap {

    private Map<String, Handler> handlerMap;

    /**
     * 初期化時にコンストラクタ内でマッピングを行う。
     * インスタンス生成後はマッピングデータを状態として保持している。
     * 制御側（ディスパッチャー（割り当て人））の呼び出しに応じて（メッセージング）ハンドラを与える。
     */
    public HandlerMap() {
        // マッピングの初期化
        // コンストラクタでprivateメソッドを読んでいる
        this.handlerMap = init();
    }

    /**
     * ハンドラとURLをマッピングする処理
     *
     * @return Map
     */
    private Map<String, Handler> init() {
        // Java SE 7 から可能になったダイヤモンド演算子
        final Map<String, Handler> handlerMap = new HashMap<String, Handler>(){
            {
                Handler dynamicContentHandler = new DynamicContentHandler();
                Handler staticContentHandler = new StaticContentHandler();

                put("program/board", dynamicContentHandler);
                put("/", staticContentHandler);
            }
        };
        return handlerMap;
    }

    public Handler getHandler(String url) {
        Handler handler = this.handlerMap.get(url);
        return handler;
    }
}
// 議論：Mapにはfinalをつけて、クラスが確保するメモリに帰するデータ構造にしよう。
// finalをつけると静的メンバとなる
// 議論：匿名クラスを使おう
// 議論：URLパターンを考える
// テスト
// リファクタリング


