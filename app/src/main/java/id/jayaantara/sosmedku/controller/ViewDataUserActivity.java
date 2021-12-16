package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;
import id.jayaantara.sosmedku.controller.auth.Auth;

public class ViewDataUserActivity extends AppCompatActivity {

    private long curent_user;
    private TextView tv_email, tv_username;
    private Button btn_logout, btn_edit, btn_hapus;

    DBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_user);

        dbHandler = new DBHandler(this);
        curent_user = Auth.getInstance().getPreferenceCurentUser(ViewDataUserActivity.this);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_username = (TextView) findViewById(R.id.tv_username);

        getUser();

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.getInstance().logout(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManajemenUserActivity.class);
                startActivity(intent);
            }
        });

        btn_hapus = findViewById(R.id.btn_hapus);
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAkun();
            }
        });

    }

    private void deleteAkun(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin mau hapus akun ini?");
        alertDialogBuilder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dbHandler.deleteUser(curent_user);
                Auth.getInstance().logout(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
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

    private void getUser(){
        Cursor cursor = dbHandler.getUser(curent_user);
        if(cursor.moveToFirst()){
            String email = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_email));
            String username = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_username));

            tv_email.setText(email);
            tv_username.setText(username);

        }
    }
}