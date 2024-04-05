package estaciones2.bici.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import estaciones2.bici.modelo.Bici;

public class BiciDto {
    private String codigo;
    private String modelo;
    private String fechaAlta;
    private String fechaBaja;
    private String motivoBaja;
    private boolean disponible;
    private String estacion;
    private List<IncidenciaDto> incidencias;

    public BiciDto(String codigo, String modelo, String fechaAlta, String fechaBaja, String motivoBaja,
            boolean disponible,
            String estacion, List<IncidenciaDto> incidencias) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
        this.disponible = disponible;
        this.estacion = estacion;
        this.incidencias = incidencias;
    }

    public BiciDto(String codigo, String modelo, LocalDate fechaAlta, LocalDate fechaBaja, String motivoBaja,
            boolean disponible,
            String estacion, List<IncidenciaDto> incidencias) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta.toString();
        if(fechaBaja!=null)
            this.fechaBaja = fechaBaja.toString();
        else
            this.fechaBaja=null;
        this.motivoBaja = motivoBaja;
        this.disponible = disponible;
        this.estacion = estacion;
        this.incidencias = incidencias;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getEstacion() {
        return estacion;
    }

    public List<IncidenciaDto> getIncidencias() {
        return incidencias;
    }

    public static BiciDto deEntidad(Bici bici, String idEstacion) {
        List<IncidenciaDto> incidenciasDTO = bici.getIncidencias().stream()
                .map(incidencia -> new IncidenciaDto(incidencia.getId(), incidencia.getFechaAlta().toString(),
                        incidencia.getFechaCierre().toString(),
                        incidencia.getDescripcion(), incidencia.getEstado().toString(), incidencia.getIdBici()))
                .collect(Collectors.toList());

        return new BiciDto(bici.getCodigo(), bici.getModelo(), bici.getFechaAlta(),
                bici.getFechaBaja(), bici.getMotivoBaja(), bici.isDisponible(), idEstacion, incidenciasDTO);
    }
}
