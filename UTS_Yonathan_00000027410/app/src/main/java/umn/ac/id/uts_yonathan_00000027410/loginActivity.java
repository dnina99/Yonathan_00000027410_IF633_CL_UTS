package umn.ac.id.uts_yonathan_00000027410;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    private Button btnLogin2;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin2 = findViewById(R.id.btnLogin2);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        getSupportActionBar().setTitle("Halaman Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        //if(playerActivity.mPlayer != null){
        //    playerActivity.mPlayer.pause();
        //}


        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    if(username.getText().toString().equals("uasmobile") && password.getText().toString().equals("uasmobilegenap")){
                        Intent intent = new Intent(loginActivity.this, laguActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(v.getContext(), "Login Gagal", Toast.LENGTH_LONG).show();
                    }
                }
                //Intent intent = new Intent(loginActivity.this, laguActivity.class);
                //startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}