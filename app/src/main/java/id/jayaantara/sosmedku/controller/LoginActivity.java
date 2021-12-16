package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;
import id.jayaantara.sosmedku.controller.auth.Auth;

public class LoginActivity extends AppCompatActivity {

    private Auth login;
    private EditText edt_username, edt_password;
    private TextView tv_register;
    private Button btn_login;
    private String username, password;
    DBHandler dbHandler;

    private static final Integer STATUS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHandler = new DBHandler(this);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_register =findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toRegister();
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getLogin();
            }
        });
    }


    private void toDashboard() {
        Intent intent= new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
    }

    private void toRegister() {
        Intent intent= new Intent(getApplicationContext(), ManajemenUserActivity.class);
        startActivity(intent);
    }

    public void getLogin(){
        username = edt_username.getText().toString();
        password = edt_password.getText().toString();

        Auth auth_login = new Auth();
        login = auth_login.login(LoginActivity.this, username, password);

        if(login == null){
            edt_username.setError("Mungkin username kamu salah");
            edt_password.setError("Atau pasword kamu salah");
        }else{
            Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
            toDashboard();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }


}