package com.example.pc.medicamentos.entities;

import android.text.Editable;

import java.io.Serializable;

public class Medicamento implements Serializable {
    String nombre,fechaInicio,HoraInicio;
    int cantidad,frecuencia;
    private static Medicamento instance = null;

    public Medicamento() {
    }

    public Medicamento(String nombre, String fechaInicio, String horaInicio, int cantidad, int frecuencia) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        HoraInicio = horaInicio;
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
    }


    public static Medicamento getInstance() {
        if(instance == null) {instance = new Medicamento(); }
        return instance;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
