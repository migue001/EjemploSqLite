package com.example.ejemplosqlite;

import android.graphics.Bitmap;

public class AlumnoModelo {
    private String numCuenta,nombre,carrera;
    private Bitmap imagen;
    public AlumnoModelo(){}

    public AlumnoModelo (String numCuenta, String nombre, String carrera, Bitmap img)
    {
        this.numCuenta = numCuenta;
        this.nombre = nombre;
        this.carrera = carrera;
        this.imagen = img;


    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
