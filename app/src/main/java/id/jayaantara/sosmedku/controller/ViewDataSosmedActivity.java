package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;


public class ViewDataSosmedActivity extends AppCompatActivity {

    private TextView tv_inisial, tv_sosmed, tv_email, tv_username, tv_password, tv_status, tv_prioritas, tv_tanda;
    private Button btn_kembali, btn_edit;
    private long id;
    private Intent intent;
    DBHandler dbHandler;

    public static final String EXTRA_MESSAGE_INISIAL= "id.jayaantara.tinggalin.controller.MESSAGE_INISIAL";
    public static final String EXTRA_MESSAGE_EMAIL= "id.jayaantara.tinggalin.controller.MESSAGE_EMAIL";
    public static final String EXTRA_MESSAGE_SOSMED= "id.jayaantara.tinggalin.controller.MESSAGE_SOSMED";
    public static final String EXTRA_MESSAGE_USERNAME= "id.jayaantara.tinggalin.controller.MESSAGE_USERNAME";
    public static final String EXTRA_MESSAGE_PASSWORD= "id.jayaantara.tinggalin.controller.MESSAGE_PASSWORD";
    public static final String EXTRA_MESSAGE_STATUS= "id.jayaantara.tinggalin.controller.MESSAGE_STATUS";
    public static final String EXTRA_MESSAGE_PRIORITAS= "id.jayaantara.tinggalin.controller.MESSAGE_PRIORITAS";
    public static final String EXTRA_MESSAGE_TANDA= "id.jayaantara.tinggalin.controller.MESSAGE_TANDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_sosmed);

        dbHandler = new DBHandler(this);

        tv_inisial = (TextView) findViewById(R.id.tv_inisial_akun);
        tv_sosmed = (TextView) findViewById(R.id.tv_jenis_sosmed);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_status = (TextView) findViewById(R.id.tv_status_akun);
        tv_prioritas = (TextView) findViewById(R.id.tv_skala_prioritas);
        tv_tanda = (TextView) findViewById(R.id.tv_tanda_bisnis);

        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDataSosmedActivity.this, ManajemenDataSosmedActivity.class);
                intent.putExtra(DBHandler.row_id_sosmed, id);
                startActivity(intent);
            }
        });

        btn_kembali = findViewById(R.id.btn_kembali);
        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        intent = getIntent();
        if(getIntent().hasExtra(EXTRA_MESSAGE_INISIAL) && getIntent().hasExtra(EXTRA_MESSAGE_EMAIL) && getIntent().hasExtra(EXTRA_MESSAGE_EMAIL) &&getIntent().hasExtra(EXTRA_MESSAGE_SOSMED) &&getIntent().hasExtra(EXTRA_MESSAGE_USERNAME) &&getIntent().hasExtra(EXTRA_MESSAGE_PASSWORD) &&getIntent().hasExtra(EXTRA_MESSAGE_PRIORITAS) &&getIntent().hasExtra(EXTRA_MESSAGE_TANDA)){
            getDataIntent();
        }else if (intent.hasExtra(DBHandler.row_id_sosmed)){
            id = intent.getLongExtra(DBHandler.row_id_sosmed,0);
            getDataDB();
        }
    }

    private void getDataIntent(){

        String inisial = getIntent().getStringExtra(EXTRA_MESSAGE_INISIAL);
        String sosmed = getIntent().getStringExtra(EXTRA_MESSAGE_SOSMED);
        String email = getIntent().getStringExtra(EXTRA_MESSAGE_EMAIL);
        String username = getIntent().getStringExtra(EXTRA_MESSAGE_USERNAME);
        String password = getIntent().getStringExtra(EXTRA_MESSAGE_PASSWORD);
        String status = getIntent().getStringExtra(EXTRA_MESSAGE_STATUS);
        String prioritas = getIntent().getStringExtra(EXTRA_MESSAGE_PRIORITAS);
        String tanda = getIntent().getStringExtra(EXTRA_MESSAGE_TANDA);

        tv_inisial.setText(inisial);
        tv_sosmed.setText(sosmed);
        tv_email.setText(email);
        tv_username.setText(username);
        tv_password.setText(password);
        tv_status.setText(status);
        tv_prioritas.setText(prioritas);
        tv_tanda.setText(tanda);

        btn_edit.setVisibility(View.GONE);
    }

    private void getDataDB(){
        Cursor cursor = dbHandler.getDataSosmed(id);
        if(cursor.moveToFirst()){
            String inisial = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_inisial));
            String sosmed = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_sosmed));
            String email = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_email_sosmed));
            String username = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_username_sosmed));
            String password = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_password_sosmed));
            String status = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_status));
            String prioritas = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_prioritas));
            String tanda_bisnis = cursor.getString((Integer) cursor.getColumnIndex(DBHandler.row_tanda_bisnis));

            tv_inisial.setText(inisial);
            tv_sosmed.setText(sosmed);
            tv_email.setText(email);
            tv_username.setText(username);
            tv_password.setText(password);
            tv_status.setText(status);
            tv_prioritas.setText(prioritas);
            tv_tanda.setText(tanda_bisnis);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Application On Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Application On Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Application On Restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Application On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Application On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Application On Destroy", Toast.LENGTH_SHORT).show();
    }
}