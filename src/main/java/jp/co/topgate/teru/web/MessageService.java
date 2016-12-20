package jp.co.topgate.teru.web;

/**
 * Created by e125761 on 2016/12/18.
 */
public class MessageService {
    private String[] messages;
    MessageService() {
        String messages[] = {"hello", "hey", "hi"};
        this.messages = messages;
    }

    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message: this.messages) {
            stringBuilder.append("<h1>" + message + "</h1>");
        }
        return stringBuilder.toString();
    }
}
う