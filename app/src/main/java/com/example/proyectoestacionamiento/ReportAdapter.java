package com.example.proyectoestacionamiento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Object> items; // Puede contener elementos de tipo ReportItem o String (encabezados)

    public ReportAdapter(List<Object> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View itemView = inflater.inflate(R.layout.item_report, parent, false);
            return new ReportViewHolder(itemView);
        }
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            // Configurar el encabezado aquí
            ((HeaderViewHolder) holder).textHeader.setText((String) item);
        } else if (holder instanceof ReportViewHolder) {
            // Configurar el elemento de informe aquí
            ReportItem reportItem = (ReportItem) item;
            ((ReportViewHolder) holder).textFecha.setText(reportItem.getFecha());
            ((ReportViewHolder) holder).textPromedioDuracion.setText(String.valueOf(reportItem.getPromedioDuracion()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        return (item instanceof String) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            textHeader = itemView.findViewById(R.id.textHeader);
        }
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView textFecha;
        TextView textPromedioDuracion;

        ReportViewHolder(View itemView) {
            super(itemView);
            textFecha = itemView.findViewById(R.id.textFecha);
            textPromedioDuracion = itemView.findViewById(R.id.textPromedioDuracion);
        }
    }
}
