package pl.krysinski.restapicassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import pl.krysinski.restapicassandra.model.Message;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends CassandraRepository<Message, UUID> {

    @Query("SELECT title, content FROM Message WHERE email = ?2 ALLOW FILTERING")
    List<Message> findMessagesByEmail(String email);

    @Query("SELECT * FROM Message WHERE magic_number = ?0 ALLOW FILTERING")
    List<Message> findMessagesByMagicNumber(Integer magic_number);


}
