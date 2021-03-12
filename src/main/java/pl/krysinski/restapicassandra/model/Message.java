package pl.krysinski.restapicassandra.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;


@Table
public class Message {

    @PrimaryKey
    UUID id;

    String email;
    String title;
    String content;
    Integer magic_number;

    public Message(String email, String title, String content, Integer magic_number) {
        this.id = Uuids.timeBased();
        this.email = email;
        this.title = title;
        this.content = content;
        this.magic_number = magic_number;
    }

    public Message() {
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id = Uuids.timeBased();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMagic_number() {
        return magic_number;
    }

    public void setMagic_number(Integer magic_number) {
        this.magic_number = magic_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", magic_number=" + magic_number +
                '}';
    }
}
