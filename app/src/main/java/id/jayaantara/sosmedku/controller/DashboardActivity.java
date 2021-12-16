package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;
import id.jayaantara.sosmedku.adapters.DataAdapter;
import id.jayaantara.sosmedku.controller.auth.Auth;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView rv_data;
    private EditText edt_search;
    private FloatingActionButton btn_tambah;
    private DataAdapter dataAdapter;
    private ImageButton btn_user;
    private long curent_user;
    private AlertDialog.Builder dialog;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dbHandler = new DBHandler(this);

        curent_user = Auth.getInstance().getPreferenceCurentUser(DashboardActivity.this);

        rv_data = findViewById(R.id.rv_data);

        rv_data.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, dbHandler.getDataSosmedByUserId(curent_user));
        rv_data.setAdapter(dataAdapter);

        btn_user = findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ViewDataUserActivity.class));
            }
        });

        btn_tambah = findViewById(R.id.btn_tambah);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ManajemenDataSosmedActivity.class));
            }
        });

        dataAdapter.setDialog(new DataAdapter.Dialog() {
            @Override
            public void onClick(long id) {
                final CharSequence[] dialogItem ={"Lihat","Edit","Hapus"};
                dialog = new AlertDialog.Builder(DashboardActivity.this);
                dialog.setItems(dialogItem,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(DashboardActivity.this, ViewDataSosmedActivity.class);
                                intent.putExtra(DBHandler.row_id_sosmed, id);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent2 = new Intent(DashboardActivity.this, ManajemenDataSosmedActivity.class);
                                intent2.putExtra(DBHandler.row_id_sosmed, id);
                                startActivity(intent2);
                                break;
                            case 2:
                                deleteData(id);
                                break;
                        }
                    }
                });

                dialog.show();
            }
        });

        edt_search = (EditText) findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void deleteData(long id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin mau hapus yang ini?");
        alertDialogBuilder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dbHandler.deleteDataSosmed(id);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("enggak",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataAdapter.swapCursor(dbHandler.getDataSosmedByUserId(curent_user));
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }
}