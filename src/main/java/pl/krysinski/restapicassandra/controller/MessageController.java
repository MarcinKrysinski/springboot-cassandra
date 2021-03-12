package pl.krysinski.restapicassandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krysinski.restapicassandra.model.Message;
import pl.krysinski.restapicassandra.repository.MessageRepository;
import pl.krysinski.restapicassandra.service.MailService;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageRepository messageRepository;
    private final MailService mailService;

    @Autowired
    public MessageController(MessageRepository messageRepository, MailService mailService) {
        this.messageRepository = messageRepository;
        this.mailService = mailService;
    }


    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message messageWithId = new Message(message.getEmail(), message.getTitle(), message.getContent(), message.getMagic_number());
        return new ResponseEntity<>(messageRepository.save(messageWithId), HttpStatus.CREATED);
    }

    @PostMapping("/send/{magic_number}")
    public ResponseEntity<String> send(@PathVariable Integer magic_number) {
        List<Message> messages = messageRepository.findMessagesByMagicNumber(magic_number);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Message message : messages) {
            try {
                mailService.sendMail(message.getEmail(), message.getTitle(), message.getContent());
                messageRepository.delete(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/messages/{email}")
    public ResponseEntity<List<Message>> getMessageByEmail(@PathVariable String email) {
        List<Message> messages = messageRepository.findMessagesByEmail(email);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages") // niepotrzebne
    public ResponseEntity<List<Message>> getAll() {
        List<Message> messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/message/{magic_number}") //niepotrzebne
    public ResponseEntity<List<Message>> getAll(@PathVariable Integer magic_number) {
        List<Message> messages = messageRepository.findMessagesByMagicNumber(magic_number);
        for (Message m : messages) {
            messageRepository.delete(m);
        }
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }


}
