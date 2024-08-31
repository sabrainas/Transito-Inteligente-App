package com.example.transitointeligente;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {

    private TextView txtTimer;
    private TextView txtResult;
    private ImageView imgShowImage;
    private HashMap<String, Integer> listaPlacas = new HashMap<>();
    private ArrayList<String> listaDescricoes = new ArrayList<>();
    private int index;
    private Button btn1, btn2, btn3, btn4;
    private TextView txtPoint;
    private int point;
    private CountDownTimer countDownTimer;
    private long millisUntilFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_game);

        txtTimer = findViewById(R.id.txtTimer);
        txtResult = findViewById(R.id.txtResult);
        imgShowImage = findViewById(R.id.imgShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        txtPoint = findViewById(R.id.txtPoint);
        index = 0;

        // Adiciona as placas com suas respectivas imagens ao HashMap
        listaPlacas.put("Obras à frente", R.drawable.obra);
        listaPlacas.put("Curva Sinuosa", R.drawable.curva);
        listaPlacas.put("Proibido Estacionar e Embarcar", R.drawable.estacionar);
        listaPlacas.put("Semáforo à frente", R.drawable.semaforo);
        listaPlacas.put("Área Escolar", R.drawable.escolar);

        // Preenche a lista de descrições
        listaDescricoes.addAll(listaPlacas.keySet());
        Collections.shuffle(listaDescricoes);
        millisUntilFinish = 10000;
        point = 0;
        startGame();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void generateQuestions(int index) {
        ArrayList<String> tempoListaPlacas = new ArrayList<>(listaDescricoes);
        String correctAnswer = listaDescricoes.get(index);
        tempoListaPlacas.remove(correctAnswer);
        Collections.shuffle(tempoListaPlacas);
        ArrayList<String> novaLista = new ArrayList<>();
        novaLista.add(tempoListaPlacas.get(0));
        novaLista.add(tempoListaPlacas.get(1));
        novaLista.add(tempoListaPlacas.get(2));
        novaLista.add(correctAnswer);
        Collections.shuffle(novaLista);
        btn1.setText(novaLista.get(0));
        btn2.setText(novaLista.get(1));
        btn3.setText(novaLista.get(2));
        btn4.setText(novaLista.get(3));
        imgShowImage.setImageResource(listaPlacas.get(correctAnswer));
    }

    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = listaDescricoes.get(index);
        if (answer.equals(correctAnswer)) {
            point++;
            txtPoint.setText(point + " / " + listaDescricoes.size());
            txtResult.setText("Correto");
        } else {
            txtResult.setText("Errado");
        }
    }

    public void nextQuestion(View view) {
        countDownTimer.cancel();
        index++;
        if (index > listaDescricoes.size() - 1) {
            imgShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("pontos", point);
            startActivity(intent);
            finish();
        } else {
            startGame();
        }
    }

    private void startGame() {
        millisUntilFinish = 10000;
        txtTimer.setText("" + (millisUntilFinish / 1000) + "s");
        txtPoint.setText(point + " / " + listaDescricoes.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinish, 1000) {
            @Override
            public void onTick(long millisUntilFinish) {
                txtTimer.setText("" + (millisUntilFinish / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > listaDescricoes.size() - 1) {
                    imgShowImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartGame.this, GameOver.class);
                    intent.putExtra("pontos", point);
                    startActivity(intent);
                    finish();
                } else {
                    startGame();
                }
            }
        }.start();
    }

}
