package com.example.proyectoestacionamiento;

public class ReportItem {
    private String fecha;
    private int promedioDuracion;

    public ReportItem(String fecha, int promedioDuracion) {
        this.fecha = fecha;
        this.promedioDuracion = promedioDuracion;
    }

    public String getFecha() {
        return fecha;
    }

    public int getPromedioDuracion() {
        return promedioDuracion;
    }
}
