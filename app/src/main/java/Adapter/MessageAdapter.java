package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MessageDetails;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<MessageDetails> msglist;
    public static final int msg_left=0;
    public static final int msg_right=1;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context context,List<MessageDetails> msglist) {
        this.context=context;
        this.msglist=msglist;
    }

    @NonNull
    @NotNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==1) {
            view = LayoutInflater.from(context).inflate(R.layout.sender_chats, parent, false);
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.receiver_chats, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        MessageDetails messageDetails=msglist.get(position);
        holder.showmsg.setText(messageDetails.getMessage());
    }

    @Override
    public int getItemCount() {
        return msglist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView showmsg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            showmsg=itemView.findViewById(R.id.show_msg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(msglist.get(position).getSender().equals(firebaseUser.getUid()))
            return msg_right;
        else
            return msg_left;
    }
}
