package com.example.ejemplosqlite;

public class AlumnoModelo {
    private String numCuenta,nombre,carrera;
    public AlumnoModelo(){}

    public AlumnoModelo (String numCuenta, String nombre, String carrera)
    {
        this.numCuenta = numCuenta;
        this.nombre = nombre;
        this.carrera = carrera;

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
