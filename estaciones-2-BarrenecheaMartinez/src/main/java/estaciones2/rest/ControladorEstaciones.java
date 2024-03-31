package estaciones2.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones2.dto.EstacionDto;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.estacion.servicio.IServicioEstaciones;
import estaciones2.repositorio.RepositorioException;

@RestController
@RequestMapping("/api/estaciones")
public class ControladorEstaciones {
	
	private IServicioEstaciones servEstaciones;
	
	@Autowired
	public ControladorEstaciones(IServicioEstaciones servEstaciones)
	{
		this.servEstaciones = servEstaciones;
	}
	
	@PostMapping
	public ResponseEntity<Void> crearEstacion(@RequestBody EstacionDto estacion) throws RepositorioException
	{
		String id = servEstaciones.altaEstacion(estacion.getNombre(), estacion.getNumPuestos(), estacion.getDirPostal(), 
				estacion.getLatitud(), estacion.getLongitud());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}
	
	@GetMapping("/{id}")
	public EstacionDto getEstacion(@PathVariable String id) throws Exception
	{
		Estacion estacion = servEstaciones.obtenerEstacion(id);
		return EstacionDto.deEntidad(estacion);
	}
	
}
