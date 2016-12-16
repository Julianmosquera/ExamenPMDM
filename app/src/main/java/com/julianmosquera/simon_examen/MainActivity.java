package com.julianmosquera.simon_examen;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {



    MediaPlayer alerta1;
    MediaPlayer alerta2;
    MediaPlayer alarma3;
    MediaPlayer alarma4;

    Button botonrojo;
    Button botonazul;
    Button botonmarron;
    Button botonverde;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonrojo=(Button)findViewById(R.id.rojo);
        botonazul=(Button)findViewById(R.id.Azul);
        botonmarron=(Button)findViewById(R.id.marron);
        botonverde=(Button)findViewById(R.id.verde);


        alerta1= MediaPlayer.create(this, R.raw.alerta1);
        alerta2=MediaPlayer.create(this, R.raw.alerta2);
        alarma3=MediaPlayer.create(this, R.raw.alarma3cortamp3);
        alarma4=MediaPlayer.create(this, R.raw.gongcorto);


    }

    int [] botones= {R.id.Azul,R.id.rojo,R.id.marron,R.id.verde};
    String [] colorBoton={"#03a9f4","#e53935","#795548","#7cb342"};


    int [] colorClaro={Color.BLUE,Color.RED,Color.parseColor("#FFFF00"),Color.GREEN};
    int [] arrayAudio ={R.raw.alerta1,R.raw.alerta2,R.raw.alarma3cortamp3,R.raw.gongcorto};

    TimerTask tiempoTarea;
    Timer tiempo;
    ArrayList<Integer> colores = new ArrayList();
    ArrayList<Integer> jugador = new ArrayList();


    protected static int NIVEL=1;
    protected static int CONTADOR = 0;
    protected static int CONTTIEMPO=0;
    protected static int DIFICULT=4;



    void Start(View e) {
        findViewById(R.id.Azul).setEnabled(true);
        findViewById(R.id.rojo).setEnabled(true);
        findViewById(R.id.marron).setEnabled(true);
        findViewById(R.id.verde).setEnabled(true);
        CONTADOR = 0;
        empezarTimer();
        e.setEnabled(false);
    }



    void eventoRojo(View r) {
        CONTADOR++;
        jugador.add(1);
        parpadear(1);
        Reboot();
    }

    void eventomarron(View am) {
        CONTADOR++;
        jugador.add(2);
        parpadear(2);
        Reboot();
    }


    void eventoAzul(View a) {
        CONTADOR++;
        jugador.add(0);
        parpadear(0);
        Reboot();
    }

    void eventoVerde(View a) {
        CONTADOR++;
        jugador.add(3);
        parpadear(3);
        Reboot();
    }




    public void check() {

        TextView nivel = (TextView) findViewById(R.id.nivel);
        if (colores.toString().equals(jugador.toString())) {
            Toast.makeText(this, "Win", Toast.LENGTH_SHORT).show();
            DIFICULT++;
            NIVEL++;
            nivel.setText("Nivel: "+NIVEL);
            Intent intent=new Intent(this,segun.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Lose", Toast.LENGTH_SHORT).show();
            DIFICULT=4;
            NIVEL=1;
            nivel.setText("Nivel: "+NIVEL);}
        findViewById(R.id.Azul).setEnabled(false);
        findViewById(R.id.rojo).setEnabled(false);
        findViewById(R.id.marron).setEnabled(false);
        findViewById(R.id.verde).setEnabled(false);
    }


    public void parpadear(final int posicionBotn){
        findViewById(botones[posicionBotn]).setBackgroundColor(colorClaro[posicionBotn]);
        final MediaPlayer audio = MediaPlayer.create(this, arrayAudio[posicionBotn]);
        audio.start();
        findViewById(botones[posicionBotn]).postDelayed(new Runnable() {
            public void run() {
                audio.reset();
                findViewById(botones[posicionBotn]).setBackgroundColor(Color.parseColor(colorBoton[posicionBotn]));
            }
        }, 600);

    }

    public void Reboot(){
        Button start = (Button) findViewById(R.id.Start);
        if (CONTADOR == DIFICULT) {
            check();
            colores.clear();
            jugador.clear();
            CONTTIEMPO=0;
            start.setEnabled(true);
        }
    }


    public void inicializarTimer (){
        tiempoTarea = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int numeroAlea=aleatorio();
                        colores.add(numeroAlea);
                        if(numeroAlea==0){
                            parpadear(0);}
                        if(numeroAlea==1){
                            parpadear(1);
                        }
                        if(numeroAlea==2){
                            parpadear(2);
                        }
                        if(numeroAlea==3){
                            parpadear(3);
                        }

                        CONTTIEMPO++;
                        if(CONTTIEMPO==DIFICULT){
                            pararTimer();
                        }

                    }
                });

            }
        };


    }
    public void empezarTimer(){
        tiempo = new Timer();
        inicializarTimer();
        tiempo.schedule(tiempoTarea, 200, 1000);
    }
    public void pararTimer(){
        if (tiempo !=null){
            tiempo.cancel();
            tiempo= null;
        }
    }


    int aleatorio() {

        int valorDado = (int) Math.floor(Math.random()*4);
        return valorDado;

    }

}
