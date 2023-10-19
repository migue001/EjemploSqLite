package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText numCuenta, nombre,carrera;
    ImageButton buscar;
    Button eliminar, editar,agregarImagen;
    DeveloperBD db;
    String x;
    ImageView fotoP;
    private static final int PICK_IMAGE = 100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         numCuenta = findViewById(R.id.numeroCuenta);
         nombre = findViewById(R.id.nombre);
         carrera = findViewById(R.id.carrera);
        buscar = findViewById(R.id.botonBuscar);
        eliminar = findViewById(R.id.btneliminar);
        editar = findViewById(R.id.btnmodificar);
        agregarImagen = findViewById(R.id.agregarImg);
        fotoP = findViewById(R.id.foto);
        db = new DeveloperBD(this);
        agregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlumnoModelo alumno = new AlumnoModelo();
                final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
                developerBD.buscarAlumnos(alumno, numCuenta.getText().toString());
                nombre.setText(alumno.getNombre());
                carrera.setText(alumno.getCarrera());
                fotoP.setImageBitmap(alumno.getImagen());

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
                if (fotoP.getDrawable()!=null) {


                    try {

                        FileInputStream fs = new FileInputStream(x);
                        byte[] imgbyte = new byte[fs.available()];
                        fs.read(imgbyte);
                        ContentValues values = new ContentValues();
                        values.put("NOMBRE", nombre.getText().toString());
                        values.put("CARRERA",carrera.getText().toString());
                        values.put("IMG",imgbyte);
                        String whereClause = "NUM=?"; // Reemplaza "numCuenta" con el nombre de tu columna de número de cuenta
                        String[] whereArgs = {numCuenta.getText().toString()};
                        SQLiteDatabase db = developerBD.getWritableDatabase();
                        int filasActualizadas = db.update("ALUMNOS", values, whereClause, whereArgs);
                        //developerBD.editarCursos(numCuenta.getText().toString(), nombre.getText().toString(), carrera.getText().toString(),imgbyte);
                        if (filasActualizadas>0){
                            Toast.makeText(MainActivity.this, "SE MODIFICCO CORRECTAMENTE"+imgbyte, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "NO SE AGREGOE"+imgbyte, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
                else{
                    Toast.makeText(MainActivity.this, "Falta la imagen de Agregar", Toast.LENGTH_SHORT).show();
                }
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
    private Bitmap captureBitmapFromImageView(ImageView imageView) {
        // Verifica si la imagen del ImageView está establecida
        if (imageView.getDrawable() == null) {
            return null;
        }

        // Crea un objeto Bitmap que contendrá la imagen del ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap;
    }
@Override
protected void onActivityResult(int requestCode, int resultCode,Intent data)
{
    super.onActivityResult(requestCode, resultCode,data);
    if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
    {
        Uri uri = data.getData();
        fotoP.setImageURI(uri);
        x = getPath(uri);
       Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();


    }
}

    private String getPath(Uri uri) {
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection,null,null,null);
        if(cursor!=null)
        {
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private void insertar(String numCuenta, String nombre, String carrera, String img) {
        final DeveloperBD developerBD = new DeveloperBD(getApplicationContext());
        //developerBD.agregarAlumnos(numCuenta,nombre,carrera,);
        if(db.agregarAlumnos(numCuenta,nombre,carrera,img)){
            Toast.makeText(this, "SE HA AGREGADO EL ALUMNO", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No SE AGREGO", Toast.LENGTH_SHORT).show();
        }

    }

    public void Agregar(View view)
    {
        String numCuen = numCuenta.getText().toString();
        String nom = nombre.getText().toString();
        String car = carrera.getText().toString();
        insertar(numCuen,nom,car,x);
    }
    public void ingresarMostrar(View view)
    {
        Intent ingresar = new Intent(this, Consulta.class);
        startActivity(ingresar);
    }
}