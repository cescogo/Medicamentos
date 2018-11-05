package com.example.pc.medicamentos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.pc.medicamentos.entities.Medicamento;

import java.util.Calendar;

public class AgregarMedicamentos extends AppCompatActivity {
    Calendar mcurrentDate;
    int mYear,mMonth,mDay,hour,minute;
        BaseDeDatos baseDeDatos;
    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicamentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baseDeDatos=BaseDeDatos.getInstance(this);
        mcurrentDate= Calendar.getInstance();
         mYear = mcurrentDate.get(Calendar.YEAR);
         mMonth = mcurrentDate.get(Calendar.MONTH);
         mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
         final TextView textfreq= (TextView) findViewById(R.id.text_freq);
        SeekBar barraFreq= (SeekBar) findViewById(R.id.seek_freq);
        barraFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
                textfreq.setText(pval + " h" );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pval = seekBar.getProgress();

                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final TextView agregarmed = (TextView) findViewById(R.id.text_fecha);
        agregarmed.setOnClickListener(new View.OnClickListener(){

        @Override

        public void onClick(View arg0) {

            // MensajeCalendario();


            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(AgregarMedicamentos.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    agregarmed.setText(selectedday+"/"+(selectedmonth+1)+"/"+selectedyear);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();



        }

    });

        hour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentDate.get(Calendar.MINUTE);
         final TextView agregarHora = (TextView) findViewById(R.id.text_hora);
        agregarHora.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                // TODO Auto-generated method stub


                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AgregarMedicamentos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                        {
                            agregarHora.setText(selectedHour+":0"+selectedMinute);
                        }
                        else
                        {
                            agregarHora.setText(selectedHour+":"+selectedMinute);
                        }

                      hour=selectedHour;
                      minute=selectedMinute;
                    }
                }, hour, minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }





        });
        int aux2=mMonth+1;
        agregarmed.setText(mDay+"/"+(aux2)+"/"+mYear);
        agregarHora.setText(hour+":"+minute);

        Button but_agregar= (Button) findViewById(R.id.but_agregar);
        but_agregar.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                EditText text_med= (EditText) findViewById(R.id.Edit_Nom_Med);
                final EditText text_cant= (EditText) findViewById(R.id.edit_cantidad);
                TextView text_fech= (TextView) findViewById(R.id.text_fecha);
                //TextView text_hor= (TextView) findViewById(R.id.text_hora);
                SeekBar text_freq= (SeekBar) findViewById(R.id.seek_freq);
                String auxcant= String.valueOf(text_cant.getText());
                int cant=Integer.parseInt(auxcant);
                int hor=hour*60+minute;
                baseDeDatos.getWritableDatabase();
                baseDeDatos.agregarMedicamento(new Medicamento(text_med.getText().toString(),text_fech.getText().toString() ,hor ,cant, text_freq.getProgress()));
                configAlarm(hour,minute,text_med.getText().toString());
                Intent intento = new Intent(getApplicationContext(), Calendario.class);

                startActivity(intento);
            }

        });

    }

    private void configAlarm(int hora,int minuto,String nombre)
    {
        //alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, Calendario.class);
        alarmIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

// Alarma a las 8:30 a.m.

      //  mcurrentDate.setTimeInMillis(System.currentTimeMillis());
       // mcurrentDate.set(Calendar.HOUR_OF_DAY, hora);
     //   mcurrentDate.set(Calendar.MINUTE, minuto);
// creacion de la notificazcion
        notificacion = new NotificationCompat.Builder(this);
        notificacion.setTicker("Alerta de Medicamento");
        notificacion.setPriority(Notification.PRIORITY_HIGH);
        notificacion.setAutoCancel(true);
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("Tomar Medicamento");
        notificacion.setContentText("Es hora de tomarse el medicamento: "+nombre);
        notificacion.setContentIntent(alarmIntent);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,notificacion.build());

// Repeticiones en intervalos de 20 minutos
       // alarmMgr.set(AlarmManager.RTC_WAKEUP, mcurrentDate.getTimeInMillis(),alarmIntent);
    }


}




