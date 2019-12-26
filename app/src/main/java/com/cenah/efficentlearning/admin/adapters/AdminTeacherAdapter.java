package com.cenah.efficentlearning.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.models.Student;
import com.cenah.efficentlearning.models.Teacher;

import java.util.ArrayList;

public class AdminTeacherAdapter extends RecyclerView.Adapter<AdminTeacherAdapter.ViewHolder>{
    private ArrayList<Teacher> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnClick onClick;

    public AdminTeacherAdapter(Context context, OnClick onClick, ArrayList<Teacher> data) {
        layoutInflater = LayoutInflater.from(context);
        this.datalist = data;
        setHasStableIds(true);
        this.context = context;
        this.onClick = onClick;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @NonNull
    @Override
    public AdminTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.teacher_item, parent, false);
        final AdminTeacherAdapter.ViewHolder myViewHolder = new AdminTeacherAdapter.ViewHolder(v);
        myViewHolder.lnr1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = myViewHolder.getAdapterPosition();
                onClick.click(datalist.get(position),position, v);
                return true;
            }
        });

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdminTeacherAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.setData(datalist.get(position), position);

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tx_name,tx_surname,tx_email,tx_username;
        CardView lnr1;

        ViewHolder(View itemView) {
            super(itemView);
            tx_name = itemView.findViewById(R.id.tx_name);
            tx_surname = itemView.findViewById(R.id.tx_surname);
            tx_email = itemView.findViewById(R.id.tx_email);
            tx_username = itemView.findViewById(R.id.tx_username);
            lnr1 = itemView.findViewById(R.id.lnr1);
        }

        void setData(final Teacher clicked, int position) {
            this.tx_name.setText(clicked.getName()+"");
            this.tx_surname.setText(clicked.getSurname()+"");
            this.tx_email.setText(clicked.getEmail()+"");
            this.tx_username.setText(clicked.getUserName()+"");
        }

    }



    public interface OnClick {
        void click(Teacher model, int position, View view);
    }
}

