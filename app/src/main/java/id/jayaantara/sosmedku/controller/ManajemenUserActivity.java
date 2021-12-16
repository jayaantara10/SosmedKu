package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;
import id.jayaantara.sosmedku.controller.auth.Auth;

public class ManajemenUserActivity extends AppCompatActivity {

    private EditText edt_email, edt_username, edt_password, edt_con_password;
    private TextView tv_title;
    private Button btn_cancel, btn_regist;
    private long curent_user;
    String email, username, password;

    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_user);

        dbHandler = new DBHandler(this);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_con_password = (EditText) findViewById(R.id.edt_con_password);
        tv_title = findViewById(R.id.tv_title);

        btn_regist = findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_email.length() == 0 || Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches() == false){
                    edt_email.setError("Isi kolom Email dengan benar");
                    edt_email.requestFocus();
                }else if(edt_username.length() == 0 ){
                    edt_username.setError("Mohon isi kolom Username");
                    edt_username.requestFocus();
                }else if(edt_password.length() == 0 ){
                    edt_password.setError("Mohon isi kolom Password");
                    edt_password.requestFocus();
                }else if(edt_con_password.length() == 0 ){
                    edt_con_password.setError("Mohon isi kolom Konfirmasi Password");
                    edt_con_password.requestFocus();
                }else {
                    String check_pass = edt_con_password.getText().toString();
                    if (edt_password.getText().toString().equals(check_pass)) {
                        confirmRegist();
                    } else {
                        edt_con_password.setError("Konfirmasi password salah, harap gunakan password yang sama");
                        edt_con_password.requestFocus();
                    }
                }
            }
        });



        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManajemenUserActivity.this, LoginActivity.class));
            }
        });

        curent_user = Auth.getInstance().getPreferenceCurentUser(ManajemenUserActivity.this);
        if (curent_user != 0){
            tv_title.setText(getString(R.string.str_edit_user));
            btn_regist.setText(getString(R.string.str_btn_simpan));
            getUser();
        }

    }

    private void confirmRegist() {
        TextView tv_title_popup, data_email, data_username, data_password ;

        Button btn_cancel_popup, btn_regist_popup;

        final Dialog dialog = new Dialog(ManajemenUserActivity.this);

        dialog.setContentView(R.layout.add_data_user_popup);

        tv_title_popup = (TextView) dialog.findViewById(R.id.tv_title_popup);
        data_email = (TextView) dialog.findViewById(R.id.data_email);
        data_email.setText(edt_email.getText().toString());

        data_username = (TextView) dialog.findViewById(R.id.data_username);
        data_username.setText(edt_username.getText().toString());

        data_password = (TextView) dialog.findViewById(R.id.data_password);
        data_password.setText(edt_password.getText().toString());


        btn_cancel_popup = dialog.findViewById(R.id.btn_cancel_popup);
        btn_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btn_regist_popup = dialog.findViewById(R.id.btn_regist_popup);
        btn_regist_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = data_username.getText().toString();
                email = data_email.getText().toString();
                password = data_password.getText().toString();
                ContentValues values = new ContentValues();
                values.put(DBHandler.row_username, username);
                values.put(DBHandler.row_email, email);
                values.put(DBHandler.row_password, password);
                if (curent_user != 0){
                    dbHandler.updateDataUser(values, curent_user);
                }else{
                    dbHandler.insertDataUser(values);
                }

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        if (curent_user != 0){
            tv_title_popup.setText(getString(R.string.str_edit_user));
            btn_regist_popup.setText(getString(R.string.str_btn_simpan));
            getUser();
        }
        dialog.show();
    }

    private void getUser(){
        Cursor cursor = dbHandler.getUser(curent_user);
        if(cursor.moveToFirst()){
            String email = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_email));
            String username = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_username));
            String password = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_password));

            edt_email.setText(email);
            edt_username.setText(username);
            edt_password.setText(password);
            edt_con_password.setText(password);

        }
    }

}