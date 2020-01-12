package com.cenah.efficentlearning.zpages.student.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.models.NotificationGlobalModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<NotificationGlobalModel> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnClick onClick;

    public NotificationAdapter(Context context, OnClick onClick, ArrayList<NotificationGlobalModel> data) {
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
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.notificaiton_item, parent, false);
        final NotificationAdapter.ViewHolder myViewHolder = new NotificationAdapter.ViewHolder(v);
        myViewHolder.lnr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = myViewHolder.getAdapterPosition();
                onClick.click(datalist.get(position), position, v);
                return true;
            }
        });

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.setData(datalist.get(position), position);

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tx_start_date, tx_title, tx_desc, tx_end_date;
        LinearLayout lnr, ln_date;

        ViewHolder(View itemView) {
            super(itemView);
            tx_end_date = itemView.findViewById(R.id.tx_end_date);
            tx_start_date = itemView.findViewById(R.id.tx_start_date);
            tx_title = itemView.findViewById(R.id.tx_title);
            tx_desc = itemView.findViewById(R.id.tx_desc);
            lnr = itemView.findViewById(R.id.lnr);
            ln_date = itemView.findViewById(R.id.ln_date);

        }

        void setData(final NotificationGlobalModel clicked, int position) {
            // if (!clicked.isAndroidRead()) this.lnr.setBackgroundColor(context.getResources().getColor(R.color.notification));
            // since there is 2 types of notifications
            if (clicked.getId() == null) {
                this.ln_date.setVisibility(View.GONE);
                this.tx_desc.setText(DateHelper.dateToString(clicked.getDate()) + "");
                this.tx_title.setText(clicked.getMessage() + "");

            } else {
                this.tx_start_date.setText(DateHelper.dateToString(clicked.getCreationTime()) + "");
                this.tx_end_date.setText(DateHelper.dateToString(clicked.getDeadline()) + "");

                this.tx_desc.setVisibility(View.GONE);
                this.tx_title.setText(clicked.getDescription());

            }


        }

    }


    public interface OnClick {
        void click(NotificationGlobalModel model, int position, View view);
    }
}


