package jp.co.topgate.teru.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import jp.co.topgate.teru.web.HTTPRequest;


/**
 * Created by terufumishimoji on 2016/11/22.
 */
public class Server {
    public static void main(String[] args) {
        HTTPRequest request = new HTTPRequest(InputStream inputStream);

    }
}
