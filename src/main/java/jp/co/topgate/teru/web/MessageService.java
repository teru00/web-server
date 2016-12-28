package jp.co.topgate.teru.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by e125761 on 2016/12/18.
 */
public class MessageService {

    private static in currentId = 0;
    private static List<Message> messages = new ArrayList<>();

    public static void addMessage(Message message) {
        message.setId(currentId);
        MessageService.messages.add(message);
        currentId++;
    }
    public static Message getMessage(int index) {
        return MessageService.messages.get(index);
    }

    public static void deleteMessageById(int id) {
        messages = messages.stream().filter((Message message) -> message.getId() != id).collect(Collectors.toList());
    }
    public static Stream<Message> getAllMessages() {
        return messages.stream();
    }
    public static Stream<Message> getByName(String name) {
        return messages.stream().filter((Message message) -> message.getName().equals(name));
    }
}