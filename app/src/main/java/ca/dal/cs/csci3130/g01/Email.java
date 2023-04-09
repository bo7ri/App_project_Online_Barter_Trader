package ca.dal.cs.csci3130.g01;

/**
 * A simple class that represents an email.
 */
public class Email {
    private String body;
    private String recipientEmail;
    private String senderEmail;
    private String subject;
    public Email() {
    }

    /**
     * Constructor for the Email class.
     * @param body The body of the email.
     * @param recipientEmail The email address of the email recipient.
     * @param senderEmail The email address of the email sender.
     * @param subject The subject line of the email.
     */
    public Email(String body, String recipientEmail, String senderEmail, String subject) {
        this.body = body;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
    }

    /**
     * Getter method for the email sender's address.
     * @return The email sender's address.
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Getter method for the email recipient's address.
     * @return The email recipient's address.
     */
    public String getRecipientEmail() {
        return recipientEmail;
    }

    /**
     * Getter method for the email subject line.
     * @return The email subject line.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Getter method for the email body.
     * @return The email body.
     */
    public String getBody() {
        return body;
    }
}
