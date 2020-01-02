package com.cenah.efficentlearning.zpages.teacher.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.models.MaterialAnswer;


import java.util.ArrayList;

public class TeacherAnswerAdapter extends RecyclerView.Adapter<TeacherAnswerAdapter.ViewHolder> {
    private ArrayList<MaterialAnswer> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnStudentClick onStudentClick;

    public TeacherAnswerAdapter(Context context, OnStudentClick onFileClick, ArrayList<MaterialAnswer> data) {
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

        View v = layoutInflater.inflate(R.layout.answer_item, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(v);
        myViewHolder.lnr1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        myViewHolder.btn_image.setOnClickListener(new View.OnClickListener() {
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


        TextView tx_name, tx_surname, tx_email, tx_username,tx_deadline;
        CardView lnr1;
        ImageView btn_image;

        ViewHolder(View itemView) {
            super(itemView);
            tx_name = itemView.findViewById(R.id.tx_name);
            tx_surname = itemView.findViewById(R.id.tx_surname);
            tx_email = itemView.findViewById(R.id.tx_email);
            tx_username = itemView.findViewById(R.id.tx_username);
            tx_deadline = itemView.findViewById(R.id.tx_deadline);
            lnr1 = itemView.findViewById(R.id.lnr1);
            btn_image = itemView.findViewById(R.id.btn_image);
        }

        void setData(final MaterialAnswer clicked, int position) {
            this.tx_name.setText(clicked.getName() + " "+ clicked.getSurname());
            this.tx_surname.setText(clicked.getScore()+"");
            this.tx_email.setText(DateHelper.dateToString(clicked.getCreationTime())  + "");
            this.tx_deadline.setText(clicked.getAnswer()  + "");
        }

    }


    public interface OnStudentClick {
        void onClick(MaterialAnswer model, int position, View view);
    }
}
