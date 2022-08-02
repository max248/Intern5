package com.example.intern5.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private Users senderUser;

    @ManyToOne
    @JoinColumn(name="receiver_id", nullable=false)
    private Users receiverUser;

    @Column(nullable = false, name = "theme")
    private String theme;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "send_date")
    private String sendDate;

    @Column(nullable = false, name = "read_status")
    private boolean readStatus = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(Users senderUser) {
        this.senderUser = senderUser;
    }

    public Users getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(Users receiverUser) {
        this.receiverUser = receiverUser;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }
}
