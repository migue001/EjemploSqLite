package com.example.ejemplosqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlumnoAdaptador extends RecyclerView.Adapter<AlumnoAdaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView numCuenta, nombre, carrera;
        public ViewHolder(View itemView) {
            super(itemView);
            numCuenta = itemView.findViewById(R.id.numCuenta);
            nombre = itemView.findViewById(R.id.nombreI);
            carrera = itemView.findViewById(R.id.carreraI);

        }
    }
    public List<AlumnoModelo> alumnoList;
    public AlumnoAdaptador(List<AlumnoModelo> alumnoList){this.alumnoList = alumnoList;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumnos,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.numCuenta.setText(alumnoList.get(position).getNumCuenta());
        holder.nombre.setText(alumnoList.get(position).getNombre());
        holder.carrera.setText(alumnoList.get(position).getCarrera());

    }

    @Override
    public int getItemCount() {
        return alumnoList.size();
    }
}
