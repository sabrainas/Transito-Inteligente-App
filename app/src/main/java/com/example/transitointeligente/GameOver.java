package com.example.transitointeligente;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    private TextView txtPoint, txtPersonalBest;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_over);

        int points = getIntent().getExtras().getInt("pontos");

        txtPoint = findViewById(R.id.txtPoint);
        txtPersonalBest = findViewById(R.id.txtPersonalBest);

        txtPoint.setText("" + points);
        sharedPreferences = getSharedPreferences("pref", 0);

        int pointsSP = sharedPreferences.getInt("pointsSP", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(points > pointsSP){
            pointsSP = points;
            editor.putInt("pointsSP", pointsSP);
            editor.commit();
        }

        txtPersonalBest.setText("" + pointsSP);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, StartGame.class);
        startActivity(intent);

        finish();
    }

    public void exit(View view){
        finish();
    }

}