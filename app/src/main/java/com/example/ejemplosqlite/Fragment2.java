package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Fragment2 extends Fragment {
    EditText numCuenta, nombre,carrera;
    ImageButton buscar;
    Button eliminar, editar,agregarImagen,mostrar,agregar;
    DeveloperBD db;
    String x;
    ImageView fotoP;
    private static final int PICK_IMAGE = 100;
    public Fragment2() {
        // Constructor vacío requerido
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        numCuenta = view.findViewById(R.id.numeroCuenta);
        nombre = view.findViewById(R.id.nombre);
        carrera = view.findViewById(R.id.carrera);
        buscar = view.findViewById(R.id.botonBuscar);
        eliminar = view.findViewById(R.id.btneliminar);
        editar = view.findViewById(R.id.btnmodificar);
        agregarImagen = view.findViewById(R.id.agregarImg);
        fotoP = view.findViewById(R.id.foto);
        mostrar = view.findViewById(R.id.mostrarr);
        agregar = view.findViewById(R.id.agregar);
        db = new DeveloperBD(requireContext());

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numCuen = numCuenta.getText().toString();
                String nom = nombre.getText().toString();
                String car = carrera.getText().toString();
                insertar(numCuen,nom,car,x);
            }
        });
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ingresar = new Intent(requireContext(), Consulta.class);
                startActivity(ingresar);
            }
        });
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
                final DeveloperBD developerBD = new DeveloperBD(requireContext());
                developerBD.buscarAlumnos(alumno, numCuenta.getText().toString());
                nombre.setText(alumno.getNombre());
                carrera.setText(alumno.getCarrera());
                fotoP.setImageBitmap(alumno.getImagen());

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeveloperBD developerBD = new DeveloperBD(requireContext());
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
                            Toast.makeText(requireContext(), "SE MODIFICCO CORRECTAMENTE"+imgbyte, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(requireContext(), "NO SE AGREGOE"+imgbyte, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
                else{
                    Toast.makeText(getActivity(), "Falta la imagen de Agregar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeveloperBD developerBD = new DeveloperBD(requireContext());
                if(numCuenta.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(requireContext(), "NO HAY NINGUN ALUMNO SELECCIONADO", Toast.LENGTH_SHORT).show();
                }else {
                    developerBD.eliminarCursos(numCuenta.getText().toString());
                    Toast.makeText(requireContext(), "SE HA ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode== Activity.RESULT_OK && requestCode==PICK_IMAGE)
        {
            Uri uri = data.getData();
            fotoP.setImageURI(uri);
            x = getPath(uri);
            Toast.makeText(requireContext(), x, Toast.LENGTH_SHORT).show();


        }
    }

    private String getPath(Uri uri) {
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection,null,null,null);
        if(cursor!=null)
        {
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private void insertar(String numCuenta, String nombre, String carrera, String img) {
        final DeveloperBD developerBD = new DeveloperBD(this.getContext());
        //developerBD.agregarAlumnos(numCuenta,nombre,carrera,);
        if(db.agregarAlumnos(numCuenta,nombre,carrera,img)){
            Toast.makeText(requireContext(), "SE HA AGREGADO EL ALUMNO", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(requireContext(), "No SE AGREGO", Toast.LENGTH_SHORT).show();
        }

    }

    public void Agregar(View view)
    {
        String numCuen = numCuenta.getText().toString();
        String nom = nombre.getText().toString();
        String car = carrera.getText().toString();
        insertar(numCuen,nom,car,x);
    }

}