package id.jayaantara.sosmedku.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private Dialog dialog;


    public interface Dialog{
        void onClick(long id);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }


    public DataAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull DataAdapter.DataViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }
        String inisial = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_inisial));
        String sosmed = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_sosmed));
        String email = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_email_sosmed));
        String username = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_username_sosmed));
        String password = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_password_sosmed));
        String status = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_status));
        String prioritas = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_prioritas));
        String status_bisnis = mCursor.getString((Integer) mCursor.getColumnIndex(DBHandler.row_tanda_bisnis));
        long id = mCursor.getLong((Integer) mCursor.getColumnIndex(DBHandler.row_id_sosmed));

        holder.itemView.setTag(id);
        holder.tv_inisial.setText(inisial);
        holder.tv_sosmed.setText(sosmed);
        holder.tv_username.setText(username);
        holder.tv_status_akun.setText(status);

        if (status_bisnis.matches("iya")){
            holder.tv_status_bisnis.setText("Bussiness");
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_inisial, tv_sosmed, tv_username, tv_status_akun, tv_status_bisnis;


        public DataViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            tv_inisial = itemView.findViewById(R.id.tv_inisial);
            tv_sosmed = itemView.findViewById(R.id.tv_sosmed);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_status_akun = itemView.findViewById(R.id.tv_status_akun);
            tv_status_bisnis = itemView.findViewById(R.id.tv_status_bisnis);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long position = (long) itemView.getTag();
                    if(dialog!=null){
                        dialog.onClick(position);
                    }
                }
            });

        }
    }




    public void swapCursor(Cursor newCrusor) {
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCrusor;

        if(newCrusor != null){
            this.notifyDataSetChanged();
        }
    }

}
