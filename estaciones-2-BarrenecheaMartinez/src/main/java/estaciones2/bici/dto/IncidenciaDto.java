package estaciones2.bici.dto;

import java.time.LocalDate;

public class IncidenciaDto {
    private String id;
    private String fechaAlta;
    private String fechaCierre;
    private String descripcion;
    private String estado;
    private String idBici;

    public IncidenciaDto(String id, String fechaAlta, String fechaCierre, String descripcion, String estado,
            String idBici) {
        this.id = id;
        this.fechaAlta = fechaAlta;
        this.fechaCierre = fechaCierre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idBici = idBici;
    }

    public IncidenciaDto(String id, LocalDate fechaAlta, LocalDate fechaCierre, String descripcion, String estado,
            String idBici) {
        this.id = id;
        this.fechaAlta = fechaAlta.toString();
        if(fechaCierre!=null)
            this.fechaCierre = fechaCierre.toString();
        else
            this.fechaCierre=null;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idBici = idBici;
    }

    public String getId() {
        return id;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public String getIdBici() {
        return idBici;
    }
}
