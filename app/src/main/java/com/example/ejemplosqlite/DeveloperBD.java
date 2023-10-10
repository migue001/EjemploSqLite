package com.example.ejemplosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DeveloperBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "alumnos.bd";
    private static final int VERSION_BD = 1;
    private static final String TABLA_ALUMNOS = "CREATE TABLE ALUMNOS (NUM TEXT PRIMARY KEY, NOMBRE TEXT, CARRERA TEXT);";

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

    public void agregarAlumnos(String numCuenta, String nombre, String carrera){

        SQLiteDatabase bd = getWritableDatabase();
        if (bd!=null)
        {
            bd.execSQL("INSERT INTO ALUMNOS VALUES('"+numCuenta+"', '"+nombre+"','"+carrera+"')");
            bd.close();
        }
    }
}
