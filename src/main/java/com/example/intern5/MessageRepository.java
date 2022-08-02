package com.example.intern5;

import com.example.intern5.model.Messages;
import com.example.intern5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {
    @Query("SELECT m from Messages m where m.receiverUser.id = ?1")
    List<Messages> findMessagesByReceiverID(Long receiverId);
}
