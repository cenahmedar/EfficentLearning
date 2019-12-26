package com.cenah.efficentlearning.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.models.Student;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdimStudentAdapter  extends RecyclerView.Adapter<AdimStudentAdapter.ViewHolder>{
    private ArrayList<Student> datalist;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnStudentClick onStudentClick;

    public AdimStudentAdapter(Context context,OnStudentClick onFileClick, ArrayList<Student> data) {
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

        View v = layoutInflater.inflate(R.layout.student_item, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(v);
        myViewHolder.lnr1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = myViewHolder.getAdapterPosition();
                onStudentClick.onClick(datalist.get(position),position, v);
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

        void setData(final Student clicked, int position) {
            this.tx_name.setText(clicked.getName()+"");
            this.tx_surname.setText(clicked.getSurname()+"");
            this.tx_email.setText(clicked.getEmail()+"");
            this.tx_username.setText(clicked.getUserName()+"");
        }

    }



    public interface OnStudentClick {
        void onClick(Student model, int position, View view);
    }
}
