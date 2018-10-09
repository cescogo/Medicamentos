package com.example.pc.medicamentos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.pc.medicamentos.entities.Medicamento;

import java.util.ArrayList;

/**
 * Created by pc on 28/3/2018.
 */

public class BaseDeDatos extends SQLiteOpenHelper {
    public static final String DB_NAME="MedicamentosDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLA_Producto = "Medicamentos";
    private static BaseDeDatos sInstance;

    public BaseDeDatos(Context context)
    {
        super(context,DB_NAME,null,DATABASE_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
       // droptable(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // se crea la bd en el celular

        String productos = "create table " + TABLA_Producto +
                "(" +// Define a primary key
                "nombre text, " +
                "fechaInicio text, " +
                "cantidad integer," +
                "horaInicio text,"+
                "frecuencia integer"+
                ");";
        db.execSQL(productos.toString());
        Log.i("Base de Datos", "Tabla Producto");


    }
    public void droptable(SQLiteDatabase db) // se elimina la bd local y se vuelve a crear para que no de error al querer ingresar un producto
    {

        String productos = "drop table  Producto;";
        db.execSQL(productos.toString());
        onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_Producto);
            onCreate(db);
        }
    }


    //singleton para usar la misma instancia
    public static synchronized BaseDeDatos getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BaseDeDatos(context.getApplicationContext());
        }
        return sInstance;
    }




// agregar producto

    public boolean agregarMedicamento(Medicamento e){ // se agregan l;os productosd en la bd local
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("insert into Medicamentos(nombre,fechaInicio,cantidad,horaInicio,frecuencia) values ('"+e.getNombre()+"', '"+e.getFechaInicio()+"', '"+e.getCantidad()+"','"+e.getHoraInicio()+"','"+ e.getFrecuencia()+"');");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en agregar Producto", ex);
            return false;
        }
    }

    //buscar producto

    public ArrayList<Medicamento> getListaMedicamentos(){ //se obtienen los productos de la bd local
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Medicamentos;";
            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Medicamento> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Medicamento aux=new Medicamento();
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow("fechaInicio")));
                    aux.setFrecuencia(cursor.getInt(cursor.getColumnIndexOrThrow("frecuencia")));
                    aux.setHoraInicio(cursor.getString(cursor.getColumnIndex("horaInicio")));
                    aux.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaEstudiantes", ex);
            return null;
        }
    }
}
