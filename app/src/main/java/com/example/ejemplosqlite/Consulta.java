package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity {
    private RecyclerView recyclerViewAlumno;
    private AlumnoAdaptador alumnoAdaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        recyclerViewAlumno = findViewById(R.id.recyclerAlumnos);
        recyclerViewAlumno.setLayoutManager(new LinearLayoutManager(this));
        alumnoAdaptador = new AlumnoAdaptador(obtenerAlumnos());
        recyclerViewAlumno.setAdapter(alumnoAdaptador);
    }

    private List<AlumnoModelo> obtenerAlumnos() {
        List<AlumnoModelo> alumno = new ArrayList<>();
        alumno.add(new AlumnoModelo("1970556","Jesus","ING. Software"));
        alumno.add(new AlumnoModelo("1970556","Jesus","ING. Software"));
        return alumno;
    }
}