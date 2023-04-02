package ca.dal.cs.csci3130.g01;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {

    private List<Email> emailList;

    public EmailAdapter(Inbox inbox, List<Email> emailList) {
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        Email email = emailList.get(position);
        holder.senderEmail.setText(email.getSenderEmail());
        holder.subject.setText(email.getSubject());
        holder.body.setText(email.getBody());
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    class EmailViewHolder extends RecyclerView.ViewHolder {

        TextView senderEmail;
        TextView subject;
        TextView body;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            senderEmail = itemView.findViewById(R.id.senderEmail);
            subject = itemView.findViewById(R.id.subject);
            body = itemView.findViewById(R.id.body);
        }
    }
}
