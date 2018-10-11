package com.example.pc.medicamentos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.pc.medicamentos.entities.Medicamento;

import java.util.Calendar;

public class AgregarMedicamentos extends AppCompatActivity {
    Calendar mcurrentDate;
    int mYear,mMonth,mDay,hour,minute;
    Medicamento med;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicamentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mcurrentDate= Calendar.getInstance();
         mYear = mcurrentDate.get(Calendar.YEAR);
         mMonth = mcurrentDate.get(Calendar.MONTH);
         mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
         med=Medicamento.getInstance();

        final TextView agregarmed = (TextView) findViewById(R.id.text_fecha);
        agregarmed.setOnClickListener(new View.OnClickListener(){

        @Override

        public void onClick(View arg0) {

            // MensajeCalendario();


            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(AgregarMedicamentos.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    agregarmed.setText(selectedday+"/"+selectedmonth+"/"+selectedyear);
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
                      agregarHora.setText(selectedHour+":"+selectedMinute);
                    }
                }, hour, minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }





        });
        agregarmed.setText(mDay+"/"+mMonth+"/"+mYear);
        agregarHora.setText(hour+":"+minute);
    }
}




