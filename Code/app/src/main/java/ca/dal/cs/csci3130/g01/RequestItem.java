package ca.dal.cs.csci3130.g01;

public class RequestItem {

    private String ReceiverUsername;
    private String ProviderUsername;
    private String ProductTitle;
    private String ProductDescription;
    private String RequestMessage;

    // KEEP THIS EMPTY.
    public RequestItem() { };

    // Request item constructor.
    public RequestItem(String ReceiverUsername, String ProviderUsername, String ProductTitle, String ProductDescription, String RequestMessage) {
        this.ReceiverUsername = ReceiverUsername;
        this.ProviderUsername = ProviderUsername;
        this.ProductTitle = ProductTitle;
        this.ProductDescription = ProductDescription;
        this.RequestMessage = RequestMessage;
    }

    // Getting receiver username.
    public String getReceiverUsername() {
        return ReceiverUsername;
    }

    // Getting provider username.
    public String getProviderUsername() {
        return ProviderUsername;
    }

    // Getting product title.
    public String getProductTitle() {
        return ProductTitle;
    }

    // Getting product description.
    public String getProductDescription() {
        return ProductDescription;
    }

    // Getting request message.
    public String getRequestMessage() {
        return RequestMessage;
    }

    // Setting receiver username.
    public void setReceiverUsername(String ReceiverUsername) {
        this.ReceiverUsername = ReceiverUsername;
    }

    // Setting provider username.
    public void setProviderUsername(String ProviderUsername) {
        this.ProviderUsername = ProviderUsername;
    }

    // Setting product title.
    public void setProductTitle(String ProductTitle) {
        this.ProductTitle = ProductTitle;
    }

    // Setting product description.
    public void setProductDescription(String ProductDescription) {
        this.ProductDescription = ProductDescription;
    }

    // Setting receiver message.
    public void setRequestMessage(String RequestMessage) {
        this.RequestMessage = RequestMessage;
    }
}
