package id.jayaantara.sosmedku.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.R;
import id.jayaantara.sosmedku.controller.auth.Auth;

public class ManajemenDataSosmedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText edt_inisial, edt_email, edt_username, edt_password;
    String inisial, sosmed, email, username, password, status, prioritas, tanda_bisnis;
    private TextView tv_prioritas, tv_title;;
    private RadioButton rb_status_aktif, rb_status_nonaktif;
    private RadioGroup rb_group_status;
    private Button btn_okay, btn_cancel;
    private SeekBar sb_prioritas;
    private CheckBox cb_tanda;
    private Spinner spnr_sosmed;
    private String str_sosmed;
    DBHandler dbHandler;
    long curent_user;
    private long id;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_data_sosmed);

        dbHandler = new DBHandler(this);

        intent = getIntent();
        id = intent.getLongExtra(DBHandler.row_id_sosmed,0);

        tv_title = (TextView) findViewById(R.id.tv_title);


        curent_user = Auth.getInstance().getPreferenceCurentUser(ManajemenDataSosmedActivity.this);

        edt_inisial = (EditText) findViewById(R.id.edt_inisial_akun);

        spnr_sosmed = (Spinner) findViewById(R.id.spnr_sosmed);
        spnr_sosmed.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sosmed_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_sosmed.setAdapter(adapter);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_prioritas = (TextView) findViewById(R.id.tv_prioritas);

        rb_group_status = (RadioGroup) findViewById(R.id.rb_group_status);
        rb_status_aktif = (RadioButton) findViewById(R.id.rb_aktif);
        rb_status_nonaktif = (RadioButton) findViewById(R.id.rb_nonkatif);

        cb_tanda = (CheckBox) findViewById(R.id.cb_tanda);

        sb_prioritas = (SeekBar) findViewById(R.id.sb_prioritas);
        sb_prioritas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                tv_prioritas.setText(Integer.toString(progressChangedValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                tv_prioritas.setText(Integer.toString(progressChangedValue));
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_prioritas.setText(Integer.toString(progressChangedValue));
            }
        });


        btn_okay = findViewById(R.id.btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_inisial.length() == 0 ) {
                    edt_inisial.setError("Anda belum mengisi inisial akun");
                    edt_inisial.requestFocus();
                }else if(edt_email.length() == 0 || Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches() == false){
                    edt_email.setError("Anda belum mengisi email akun");
                    edt_email.requestFocus();
                }else if(edt_username.length() == 0 ){
                    edt_username.setError("Anda belum mengisi username akun");
                    edt_username.requestFocus();
                }else if(edt_password.length() == 0 ){
                    edt_password.setError("Anda belum mengsis password akun");
                    edt_password.requestFocus();
                }else if(str_sosmed.matches("Pilih Sosial Media")){
                    Toast.makeText(ManajemenDataSosmedActivity.this, "Anda belum memilih sosmed", Toast.LENGTH_SHORT).show();
                    spnr_sosmed.requestFocus();
                }else if(Integer.parseInt(tv_prioritas.getText().toString()) < 1){
                    tv_prioritas.setError("Anda belum memberi skala prioritas");
                    sb_prioritas.requestFocus();
                }else if(rb_group_status.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(ManajemenDataSosmedActivity.this, "Anda belum memilih status sosmed", Toast.LENGTH_SHORT).show();
                }else{
                    confirmDataSosmed();
                }
            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManajemenDataSosmedActivity.this, DashboardActivity.class));
            }
        });

        if (intent.hasExtra(DBHandler.row_id_sosmed)){
            tv_title.setText(getString(R.string.str_edit_sosmed));
            btn_okay.setText(getString(R.string.str_btn_simpan));
            getData();
        }



    }

    private void getData(){
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

            edt_inisial.setText(inisial);
            edt_username.setText(username);
            edt_password.setText(password);
            edt_email.setText(email);
            spnr_sosmed.setSelection(((ArrayAdapter)spnr_sosmed.getAdapter()).getPosition(sosmed));
            if(status.matches("Aktif")){
                rb_status_aktif.setChecked(true);
            }else if(status.matches("Nonaktif")){
                rb_status_nonaktif.setChecked(true);
            }
            sb_prioritas.setProgress(Integer.parseInt(prioritas));
            if(tanda_bisnis.matches("iya")){
                cb_tanda.setChecked(true);
            }
        }
    }

    private void confirmDataSosmed() {
        TextView data_inisial, data_jenis_sosmed, data_email, data_username, data_password, data_status_akun, data_prioritas, data_tanda, tv_title_popup;

        Button btn_okey_popup, btn_cancel_popup;

        final Dialog dialog = new Dialog(ManajemenDataSosmedActivity.this);

        dialog.setContentView(R.layout.add_data_sosmed_popup);

        tv_title_popup = (TextView) dialog.findViewById(R.id.tv_title_popup);

        data_inisial = (TextView) dialog.findViewById(R.id.data_inisial);
        data_inisial.setText(edt_inisial.getText().toString());

        data_jenis_sosmed =(TextView) dialog.findViewById(R.id.data_sosmed);
        data_jenis_sosmed.setText(str_sosmed);

        data_email = (TextView) dialog.findViewById(R.id.data_email);
        data_email.setText(edt_email.getText().toString());

        data_username = (TextView) dialog.findViewById(R.id.data_username);
        data_username.setText(edt_username.getText().toString());

        data_password = (TextView) dialog.findViewById(R.id.data_password);
        data_password.setText(edt_password.getText().toString());

        data_status_akun = (TextView) dialog.findViewById(R.id.data_status_akun);
        int id_selection = rb_group_status.getCheckedRadioButtonId();
        RadioButton selection_status = (RadioButton) findViewById(id_selection);
        data_status_akun.setText(selection_status.getText().toString());

        data_prioritas = (TextView) dialog.findViewById(R.id.data_prioritas);
        data_prioritas.setText(tv_prioritas.getText().toString());

        data_tanda = (TextView) dialog.findViewById(R.id.data_status_bisnis);
        if (cb_tanda.isChecked()){
            data_tanda.setText("iya");
        }else{
            data_tanda.setText("tidak");
        }

        btn_cancel_popup = dialog.findViewById(R.id.btn_cancel_popup);
        btn_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_okey_popup = dialog.findViewById(R.id.btn_okey_popup);
        btn_okey_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inisial = data_inisial.getText().toString();
                sosmed = data_jenis_sosmed.getText().toString();
                email = data_email.getText().toString();
                username = data_username.getText().toString();
                password = data_password.getText().toString();
                status = data_status_akun.getText().toString();
                prioritas = data_prioritas.getText().toString();
                tanda_bisnis = data_tanda.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DBHandler.row_inisial, inisial);
                values.put(DBHandler.row_sosmed, sosmed);
                values.put(DBHandler.row_email_sosmed, email);
                values.put(DBHandler.row_username_sosmed, username);
                values.put(DBHandler.row_password_sosmed, password);
                values.put(DBHandler.row_status, status);
                values.put(DBHandler.row_prioritas, prioritas);
                values.put(DBHandler.row_tanda_bisnis, tanda_bisnis);
                values.put(DBHandler.row_id_user, curent_user);
                if (intent.hasExtra(DBHandler.row_id_sosmed)){
                    dbHandler.updateDataSosmed(values, id);
                }else{
                    dbHandler.insertDataSosmed(values);
                }


                Intent intent2 = new Intent(ManajemenDataSosmedActivity.this, ViewDataSosmedActivity.class);
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_INISIAL, data_inisial.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_EMAIL, data_email.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_SOSMED, data_jenis_sosmed.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_USERNAME, data_username.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_PASSWORD, data_password.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_STATUS, data_status_akun.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_PRIORITAS, data_prioritas.getText().toString());
                intent2.putExtra(ViewDataSosmedActivity.EXTRA_MESSAGE_TANDA, data_tanda.getText().toString());
                startActivity(intent2);

            }
        });
        if (intent.hasExtra(DBHandler.row_id_sosmed)){
            tv_title_popup.setText(getString(R.string.str_edit_sosmed));
            btn_okey_popup.setText(getString(R.string.str_btn_simpan));
        }
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        str_sosmed = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}