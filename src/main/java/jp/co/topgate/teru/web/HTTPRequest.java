package jp.co.topgate.teru.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;



/**
 * Created by terufumishimoji on 2016/11/28.
 */
public class HTTPRequest {
    private InputStream inputStream;

    HTTPRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    //getRequestMethod()
    String getRequestMethod() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
            String requestLine = br.readLine();
            //requestLineから次のSPまで読み込む
            //subString(int beginIndex, int lastIndex)を利用して、必要なtokenを抽出する
            //beginIndexは0で、
            String requestMethod = requestLine.substring(0, requestLine.indexOf(" "));
            return requestMethod;
        } catch (IOException e) {
            //保留
            System.err.println(e);
        }
    }
    //getRequestURI()
//    String getRequestMethod() {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(this.inputStream));
//            String requestLine = br.readLine();
//            //requestLineから次のSPまで読み込む
//            //subString(int biginIndex, int lastIndex)を利用して、必要なtokenを抽出する
//            //biginIndexは0で、
//            String requestMethod = requestLine.substring(0, requestLine.indexOf(" "));
//            return requestMethod;
//        } catch (IOException e) {
//            //保留
//            System.err.println(e);
//        }
//    }

}
