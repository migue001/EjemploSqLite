package com.example.ejemplosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "alumnos.bd";
    private static final int VERSION_BD = 2;
    private static final String TABLA_ALUMNOS = "CREATE TABLE ALUMNOS (NUM TEXT PRIMARY KEY, NOMBRE TEXT, CARRERA TEXT,IMG BLOB NOT NULL)";

    public DeveloperBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_ALUMNOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLA_ALUMNOS);
        sqLiteDatabase.execSQL(TABLA_ALUMNOS);

    }

    public Boolean agregarAlumnos(String numCuenta, String nombre, String carrera, String x){

        SQLiteDatabase bd = getWritableDatabase();


            SQLiteDatabase db = this.getWritableDatabase();
            try{
                FileInputStream fs = new FileInputStream(x);
                byte[] imgbyte = new byte[fs.available()];
                fs.read(imgbyte);
                ContentValues contentValues = new ContentValues();
                contentValues.put("NUM", numCuenta);
                contentValues.put("NOMBRE",nombre);
                contentValues.put("CARRERA",carrera);
                contentValues.put("IMG",imgbyte);
                bd.insert("ALUMNOS",null,contentValues);
                return true;
            }catch (IOException e){
                e.printStackTrace();
                Log.d("BD:", e.toString());
                return false;
            }

    }

    public List<AlumnoModelo> mostrarAlumnos()
    {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM ALUMNOS",null);
        List<AlumnoModelo> alumnoM = new ArrayList<>();

        if(cursor.moveToFirst())
        {

            do {

                byte[] img=cursor.getBlob(3);
                Bitmap bp=null;
                bp = BitmapFactory.decodeByteArray(img,0,img.length);
                alumnoM.add(new  AlumnoModelo(cursor.getString(0),cursor.getString(1), cursor.getString(2),bp));
            }while(cursor.moveToNext());
        }
        return alumnoM;
    }

    public void buscarAlumnos(AlumnoModelo alumnoModelo, String codigo)
    {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM ALUMNOS WHERE NUM ='"+codigo+"'",null);

        if(cursor.moveToFirst())
        {
            do {

                alumnoModelo.setNombre(cursor.getString(1));
                alumnoModelo.setCarrera(cursor.getString(2));
                byte[] img=cursor.getBlob(3);
                Bitmap bp=null;
                bp = BitmapFactory.decodeByteArray(img,0,img.length);
                alumnoModelo.setImagen(bp);
            }while(cursor.moveToNext());
        }

    }

    public void editarCursos(String numCuenta, String nombre, String carrera, byte[] imagen)
    {
        SQLiteDatabase bd = getWritableDatabase();
        if(bd!=null)
        {
            bd.execSQL("UPDATE ALUMNOS SET NOMBRE='"+nombre+"',CARRERA='"+carrera+"',IMG='"+imagen+"' WHERE NUM='"+numCuenta+"'");
            bd.close();
        }

    }

    public void eliminarCursos(String numCuenta){
        SQLiteDatabase bd = getWritableDatabase();
        if(bd!= null)
        {
            bd.execSQL("DELETE FROM ALUMNOS WHERE NUM='"+numCuenta+"'");
            bd.close();
        }
    }


}
