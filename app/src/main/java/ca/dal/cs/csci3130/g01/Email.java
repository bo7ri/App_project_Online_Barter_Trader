package ca.dal.cs.csci3130.g01;

public class Email {
    private String body;
    private String recipientEmail;
    private String senderEmail;
    private String subject;

    // Add a no-argument constructor
    public Email() {
    }

    public Email(String body, String recipientEmail, String senderEmail, String subject) {
        this.body = body;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
