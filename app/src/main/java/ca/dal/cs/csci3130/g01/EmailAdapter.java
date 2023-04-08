package ca.dal.cs.csci3130.g01;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * An adapter for displaying a list of emails in a RecyclerView.
 */
public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {

    private List<Email> emailList;

    /**
     * Constructor for the EmailAdapter class.
     * @param inbox The Inbox activity that this adapter is used in.
     * @param emailList The list of Email objects to display in the RecyclerView.
     */
    public EmailAdapter(Inbox inbox, List<Email> emailList) {
        this.emailList = emailList;
    }

    /**
     * Called when a new EmailViewHolder is needed for a RecyclerView item.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new EmailViewHolder that holds a View for an email item.
     */
    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for an email item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        // Return a new EmailViewHolder that holds a reference to the TextViews in the layout
        return new EmailViewHolder(view);
    }

    /**
     * Called when the contents of an EmailViewHolder need to be updated.
     * @param holder The EmailViewHolder to update.
     * @param position The position of the email item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        // Get the Email object at the current position in the email list
        Email email = emailList.get(position);
        // Set the TextViews of the corresponding EmailViewHolder to the values of the Email object
        holder.senderEmail.setText(email.getSenderEmail());
        holder.subject.setText(email.getSubject());
        holder.body.setText(email.getBody());
    }

    /**
     * Returns the number of items in the email list.
     * @return The number of items in the email list.
     */
    @Override
    public int getItemCount() {
        return emailList.size();
    }

    /**
     * A ViewHolder that holds a View for an email item in the RecyclerView.
     */
    class EmailViewHolder extends RecyclerView.ViewHolder {

        TextView senderEmail;
        TextView subject;
        TextView body;

        /**
         * Constructor for the EmailViewHolder class.
         * @param itemView The View for the email item.
         */
        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the TextViews in the layout and assign them to the corresponding instance variables
            senderEmail = itemView.findViewById(R.id.senderEmail);
            subject = itemView.findViewById(R.id.subject);
            body = itemView.findViewById(R.id.body);
        }
    }
}
