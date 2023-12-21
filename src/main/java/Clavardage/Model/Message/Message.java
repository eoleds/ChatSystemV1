package Clavardage.Model.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message {
    private UUID uuidSender;
    private UUID uuidReceiver;
    private LocalDateTime date;

    private byte[] content;


    public Message(UUID uuidSender,UUID uuidReceiver,byte[] content) {
        this.uuidSender = uuidSender;
        this.uuidReceiver = uuidReceiver;
        this.date = LocalDateTime.now();
        this.content= content;
    }


    public byte[] getContent() {
        return content;
    }

    public UUID getSender() {
        return uuidSender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UUID getReceiver() {
        return uuidReceiver;
    }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("[%s] %s -> %s", date.format(formatter), uuidSender.toString(), uuidReceiver.toString());
    }
}
