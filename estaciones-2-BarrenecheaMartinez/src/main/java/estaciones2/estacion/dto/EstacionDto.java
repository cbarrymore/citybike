package estaciones2.estacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

import estaciones2.estacion.modelo.Estacion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creacion de una nueva estacion en el sistema")
public class EstacionDto {
    @Schema(description = "Identificador de la estacion")
    private String id;
    @Schema(description = "Nombre de la estacion")
    private String nombre;
    @Schema(description = "Indica si la estacion esta llena")
    private boolean lleno;
    @Schema(description = "Direccion postal de la estacion")
    private long dirPostal;

    @BsonProperty(value = "coordenadas")
    @Schema(description = "Coordenadas de la estacion")
    private List<Double> coordenadas;
    @Schema(description = "Latitud de la estacion")
    private BigDecimal latitud;
    @Schema(description = "Longitud de la estacion")
    private BigDecimal longitud;
    @Schema(description = "Fecha de alta de la estacion")
    private LocalDate fechaAlta;

    public EstacionDto() {

    }

    public EstacionDto(String nombre, boolean lleno, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
        this.nombre = nombre;
        this.lleno = lleno;
        this.dirPostal = dirPostal;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaAlta = LocalDate.now();
        this.coordenadas = new ArrayList<Double>();
        coordenadas.add(longitud.doubleValue());
        coordenadas.add(latitud.doubleValue());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getLleno() {
        return lleno;
    }

    public void setLleno(boolean lleno) {
        this.lleno = lleno;
    }

    public long getDirPostal() {
        return dirPostal;
    }

    public void setDirPostal(long dirPostal) {
        this.dirPostal = dirPostal;
    }

    public List<Double> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Double> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public static EstacionDto deEntidad(Estacion estacion) {
        EstacionDto nueva = new EstacionDto(estacion.getNombre(), estacion.lleno(),
                estacion.getDirPostal(),
                estacion.getLatitud(),
                estacion.getLongitud());
        nueva.setId(estacion.getId());
        return nueva;
    }

}
