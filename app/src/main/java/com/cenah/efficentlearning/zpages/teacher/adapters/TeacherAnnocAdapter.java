package com.cenah.efficentlearning.zpages.teacher.adapters;

import android.content.Context;
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
import com.cenah.efficentlearning.models.Announcement;

import java.util.ArrayList;

public class TeacherAnnocAdapter extends RecyclerView.Adapter<TeacherAnnocAdapter.ViewHolder> {
    private ArrayList<Announcement> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnStudentClick onStudentClick;

    public TeacherAnnocAdapter(Context context, OnStudentClick onFileClick, ArrayList<Announcement> data) {
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

        View v = layoutInflater.inflate(R.layout.an_item, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(v);
        myViewHolder.lnr1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = myViewHolder.getAdapterPosition();
                onStudentClick.onClick(datalist.get(position), position, v);
                return true;
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


        TextView tx_deadline, tx_email;
        CardView lnr1;

        ViewHolder(View itemView) {
            super(itemView);
            tx_email = itemView.findViewById(R.id.tx_email);

            tx_deadline = itemView.findViewById(R.id.tx_deadline);
            lnr1 = itemView.findViewById(R.id.lnr1);
        }

        void setData(final Announcement clicked, int position) {
            this.tx_deadline.setText(clicked.getDescription() + " ");
            this.tx_email.setText(DateHelper.dateToString(clicked.getCreationTime())  + "");
        }

    }


    public interface OnStudentClick {
        void onClick(Announcement model, int position, View view);
    }
}
