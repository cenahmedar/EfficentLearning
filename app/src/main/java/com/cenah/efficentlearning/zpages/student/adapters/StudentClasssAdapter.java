package com.cenah.efficentlearning.zpages.student.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.models.Classes;

import java.util.ArrayList;

public class StudentClasssAdapter extends RecyclerView.Adapter<StudentClasssAdapter.ViewHolder> {
    private ArrayList<Classes> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnStudentClick onStudentClick;

    public StudentClasssAdapter(Context context, OnStudentClick onFileClick, ArrayList<Classes> data) {
        layoutInflater = LayoutInflater.from(context);
        this.datalist = data;
        setHasStableIds(true);
        this.context = context;
        this.onStudentClick = onFileClick;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.student_class_item, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(v);
        myViewHolder.lnr1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        myViewHolder.btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = myViewHolder.getAdapterPosition();
                onStudentClick.onClick(datalist.get(position), position, view);
            }
        });

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.setData(datalist.get(position), position);

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tx_name, tx_surname, tx_email, tx_username;
        CardView lnr1;
        Button btn_join;

        ViewHolder(View itemView) {
            super(itemView);
            tx_name = itemView.findViewById(R.id.tx_name);
            tx_surname = itemView.findViewById(R.id.tx_surname);
            tx_email = itemView.findViewById(R.id.tx_email);
            tx_username = itemView.findViewById(R.id.tx_username);
            lnr1 = itemView.findViewById(R.id.lnr1);
            btn_join = itemView.findViewById(R.id.btn_join);
        }

        void setData(final Classes clicked, int position) {
            this.tx_name.setText(clicked.getDescription() + "");
            this.tx_surname.setText(clicked.isActive()?"ACTIVE":"NOT ACTIVE");
            this.tx_email.setText(DateHelper.dateToString(clicked.getCreationTime())  + "");

            if(!clicked.isActive())
                tx_surname.setTextColor(context.getResources().getColor(R.color.red));
        }

    }


    public interface OnStudentClick {
        void onClick(Classes model, int position, View view);
    }
}
