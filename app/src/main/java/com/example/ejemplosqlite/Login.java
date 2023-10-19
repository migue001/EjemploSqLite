package com.example.ejemplosqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText correo, contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
    }
    public void ingresar(View view)
    {
        if(correo.getText().toString().equalsIgnoreCase("DM1p23B") && contrasena.getText().toString().equalsIgnoreCase("miguel"))
        {
            Intent intent = new Intent(this, Persianas.class);
            startActivity(intent);
        }
        else
        {
            credenciales_erroneas();
        }

    }
    private void credenciales_erroneas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Oh no!");
        builder.setMessage("Datos incorrectos");

        // Agrega el botón "Aceptar" y su controlador de clics
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar al hacer clic en "Aceptar"
                dialog.dismiss(); // Cierra la alerta
            }
        });
        // Muestra la alerta
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void  cerrar(View view)
    {
        Toast.makeText(this, "Hasta Pronto!", Toast.LENGTH_SHORT).show();
        finish();
    }
}