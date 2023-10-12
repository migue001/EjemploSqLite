package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText numCuenta, nombre,carrera;
    ImageButton buscar;
    Button eliminar, editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         numCuenta = findViewById(R.id.numeroCuenta);
         nombre = findViewById(R.id.nombre);
         carrera = findViewById(R.id.carrera);
        buscar = findViewById(R.id.botonBuscar);
        eliminar = findViewById(R.id.btneliminar);
        editar = findViewById(R.id.btnmodificar);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlumnoModelo alumno = new AlumnoModelo();
                final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
                developerBD.buscarAlumnos(alumno, numCuenta.getText().toString());
                nombre.setText(alumno.getNombre());
                carrera.setText(alumno.getCarrera());

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
                developerBD.editarCursos(numCuenta.getText().toString(),nombre.getText().toString(), carrera.getText().toString());
                Toast.makeText(MainActivity.this, "SE MODIFICCO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
                if(numCuenta.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "NO HAY NINGUN ALUMNO SELECCIONADO", Toast.LENGTH_SHORT).show();
                }else {
                    developerBD.eliminarCursos(numCuenta.getText().toString());
                    Toast.makeText(MainActivity.this, "SE HA ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void insertar(String numCuenta, String nombre, String carrera) {
        final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
        developerBD.agregarAlumnos(numCuenta,nombre,carrera);
        Toast.makeText(this, "SE HA AGREGADO EL ALUMNO", Toast.LENGTH_SHORT).show();
    }

    public void Agregar(View view)
    {
        String numCuen = numCuenta.getText().toString();
        String nom = nombre.getText().toString();
        String car = carrera.getText().toString();
        insertar(numCuen,nom,car);
    }
    public void ingresarMostrar(View view)
    {
        Intent ingresar = new Intent(this, Consulta.class);
        startActivity(ingresar);
    }
}