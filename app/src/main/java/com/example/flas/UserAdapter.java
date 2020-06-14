package com.example.flas;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    List<Token> tokenList;

    public UserAdapter(Context context, List<Token> tokenList) {
        this.context = context;
        this.tokenList = tokenList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.userdata, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Token token = tokenList.get( position );
        holder.textView.setText( token.getEmail() );
        
        holder.textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, Notification_5Activity.class );
                intent.putExtra("emailA", token.getEmail());
                intent.putExtra("uid", token.getUid());
                System.out.println( token.getUid() );
                context.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return tokenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textView = itemView.findViewById( R.id.user_list );
        }
    }
}
