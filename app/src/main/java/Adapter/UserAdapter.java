package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.R;
import com.example.chatapp.UserDetails;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<UserDetails> userslist;

    public UserAdapter(Context context,List<UserDetails> userslist) {
        this.context=context;
        this.userslist=userslist;
    }

    @NonNull
    @NotNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        UserDetails userDetails = userslist.get(position);
        holder.username.setText(userDetails.getName());
       if(userDetails.getImageURL().equals("default"))
       {
           holder.profile_pic.setImageResource(R.mipmap.cheems);
       }
       else
           Glide.with(context).load(userDetails.getImageURL()).into(holder.profile_pic);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, MessageActivity.class);
               intent.putExtra("userid",userDetails.getId());
               context.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return userslist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public CircleImageView profile_pic;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user_nm);
            profile_pic=itemView.findViewById(R.id.profile_pic);
        }
    }
}
