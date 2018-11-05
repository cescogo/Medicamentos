package com.example.pc.medicamentos;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.usage.UsageEvents;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.style.TextAppearanceSpan;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.medicamentos.entities.Medicamento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calendario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BaseDeDatos basedatos;
    Calendar mcurrentDate;
    ArrayList<Medicamento> medicamentosList = new ArrayList<Medicamento>();
    private Medicamento prod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mensajeagregar();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        basedatos=new BaseDeDatos(this);
        mcurrentDate= Calendar.getInstance();
        //basedatos.agregarMedicamento(new Medicamento("paracetamol","28/4/2018","6:00 am",30,12));
        initializeList();



    }
    public void initializeList() { //se inicializa la lista tomando los datos de la bd local del celular
        medicamentosList.clear();

        medicamentosList=basedatos.getListaMedicamentos();
        mostrarMedicamentos();
        Log.e("lista de medicamentos: ","lista de medicamentos: "+medicamentosList.size());

    }

    private void mostrarMedicamentos() { //se colocan los registros de la bd en el scroll bar creando los chech y seteando los onclick
        LinearLayout panel = (LinearLayout) findViewById(R.id.linear_producs2);


        for (int i = 0; i < medicamentosList.size(); i++) {
            final TextView ch = new TextView(this);
            ch.setId(i);
            ch.setText("nom: " + medicamentosList.get(i).getNombre());

            ch.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // Medicamento produc = Medicamento.getInstance();
                  /*  prod = medicamentosList.get(ch.getId());
                    produc.setNombre(prod.getNombre());*/
                    MensajeInformacion(ch.getId());


                    return true;
                }
            });

            if(i%2!=0)
            {
                ch.setBackgroundColor(Color.GRAY);
            }
            panel.addView(ch);




        }
    }

    public void MensajeInformacion(final int pos){ // pop up creada para tomar la cantidad de productos que desea comprar el usuario
        View view = (LayoutInflater.from(Calendario.this)).inflate(R.layout.popup_informacion, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Calendario.this);
        alertBuilder.setView(view);
        //final EditText userInput = (EditText) view.findViewById(R.id.);
        final TextView freq= (TextView) view.findViewById(R.id.text_freq_infd);
        final TextView prox= (TextView) view.findViewById(R.id.text_prox_dos);
        final TextView edit= (TextView) view.findViewById(R.id.text_nom_med);

        prod= medicamentosList.get(pos);
        int auxhour=(prod.getFrecuencia()*60+prod.getHoraInicio())/60;
        int auxmin=prod.getHoraInicio()-((prod.getHoraInicio()/60)*60);
        if(auxmin<10)
        {
            prox.setText(prox.getText()+""+auxhour+":"+"0"+auxmin);
        }
        else
        {
            prox.setText(prox.getText()+""+auxhour+":"+auxmin);
        }

        edit.setText(edit.getText()+prod.getNombre());
        freq.setText(freq.getText()+ String.valueOf(prod.getFrecuencia())+"H");
        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Producto produc= Producto.getInstance();



                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
        ;}

    public void Mensajeagregar(){ // pop up creada para tomar la cantidad de productos que desea comprar el usuario
        View view = (LayoutInflater.from(Calendario.this)).inflate(R.layout.popup_opciones, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Calendario.this);
        alertBuilder.setView(view);
        final Button agregarmed = (Button) view.findViewById(R.id.but_agregar_med);
        agregarmed.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                Intent intento = new Intent(getApplicationContext(), AgregarMedicamentos.class);
                startActivity(intento);


            }

        });
        /*final TextView edit= (TextView) view.findViewById(R.id.text_nom_med);
        prod= medicamentosList.get(pos);
        edit.setText(edit.getText()+prod.getNombre());
        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Producto produc= Producto.getInstance();



                    }
                });*/
        Dialog dialog = alertBuilder.create();
        dialog.show();
        ;}




        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    CalendarView calendario;
    Date fecha;
    TextView prueba;

}
