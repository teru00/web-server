package jp.co.topgate.teru.web;

import java.io.*;

/**
 *
 * リクエストオブジェクトを分析して、適切なレスポンスを組み立てるハンドラクラス。
 */
class HTTPRequestHandler {

    /**
     *
     * 受信したリクエストを分析して適切なレスポンスを組み立てるためのインスタンスメソッド。
     * リクエストURIに応じて、リソースファイルの読み込みを行う。
     *
     * @param request HTTPRequestオブジェクト
     * @return response HTTPResponseオブジェクト
     * @throws IOException
     */
    public HTTPResponse handle(HTTPRequest request) throws IOException {

        HTTPResponse response = new HTTPResponse();

        if ("GET".equals(request.getRequestMethod())) {

            File file = new File(request.getResourcePath());

            if (file.exists() && file.isFile()) {
                System.out.println("リソースは存在しました。");

//                InputStream inputStream = new FileInputStream(file);
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//                int len;
//                while ((len = inputStream.read()) != -1) {
//                    byteArrayOutputStream.write(len);
//                }
//                if (byteArrayOutputStream != null) {
//                    byteArrayOutputStream.flush();
//                    byteArrayOutputStream.close();
//                }
//                byte[] byteContent = byteArrayOutputStream.toByteArray();

                // fileをロードするメソッド
                byte [] byteContent = readFile(file);

                inputStream.close();
                response.setStatusLine("HTTP/1.1 200 OK");
                response.setHeader("Content-Type", response.getContentType(file.getName()));
                response.setMessageBody(byteContent);

            } else {
                System.out.println("リソースは存在しませんでした。");
                response.setStatusLine("HTTP/1.1 404 Not Found");
                response.setHeader("Content-Type", "text/html");
                response.setMessageBody("<h1>404 Not Found</h1>".getBytes());
            }

        } else {
            System.out.println("許可されていないHTTPメソッドです。");
            response.setStatusLine("HTTP/1.1 405 Method not allowed Explained");
            response.setHeader("Content-Type", "text/html");
            response.setMessageBody("<h1>HTTP 405 Error Method not allowed Explained</h1>".getBytes());
        }
        return response;
    }

    private byte[] readFile(File file) {
        // params File
        // return
        // filenameを引数にFS抽象パスを実体化したFileインスタンスを受け取る。
        // それを使って、ファイルを読み込み、読み込んだファイルを次の振る舞いの入力のデータがとして渡せるようにする。
        // ファイルの読み込みはバイナリとテキストに対応
        // 巨大なファイルをロードする時を考える必要がある。
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //
            int len;
            while ((len = inputStream.read()) != -1) {
                byteArrayOutputStream.write(len);
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
            }
        } catch () {
            // 例外が発生する原因
            // ファイルが存在しない 上の時点で調べているのでない。
            //
        }

    }
}
