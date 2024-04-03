package estaciones2.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones2.bici.dto.BiciDto;
import estaciones2.bici.modelo.Bici;
import estaciones2.estacion.dto.EstacionDto;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.estacion.servicio.IServicioEstaciones;
import estaciones2.repositorio.RepositorioException;

@RestController
@RequestMapping("/api/estaciones")
public class ControladorEstaciones {

	private IServicioEstaciones servEstaciones;

	@Autowired
	public ControladorEstaciones(IServicioEstaciones servEstaciones) {
		this.servEstaciones = servEstaciones;
	}

	@PostMapping
	public ResponseEntity<Void> crearEstacion(@RequestBody EstacionDto estacion) throws RepositorioException {
		String id = servEstaciones.altaEstacion(estacion.getNombre(), estacion.getNumPuestos(), estacion.getDirPostal(),
				estacion.getLatitud(), estacion.getLongitud());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping
	public List<EstacionDto> getEstaciones() throws Exception {
		List<Estacion> estaciones = servEstaciones.obtenerEstaciones();
		return estaciones.stream().map(EstacionDto::deEntidad).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public EstacionDto getEstacion(@PathVariable String id) throws Exception {
		Estacion estacion = servEstaciones.obtenerEstacion(id);
		return EstacionDto.deEntidad(estacion);
	}

	@GetMapping("/id/bicis")
	public List<EntityModel<BiciDto>> getBicisEstacion(@PathVariable String id) throws Exception {
		List<Bici> bicis = servEstaciones.bicisEstacionLimitado(id);
		List<BiciDto> bicisDto = bicis.stream().map(b -> BiciDto.deEntidad(b, id)).collect(Collectors.toList());
		List<EntityModel<BiciDto>> emBicisDto;
		emBicisDto = bicisDto.stream().map(b -> EntityModel.of(b)).collect(Collectors.toList());
		emBicisDto.forEach(b -> {
			try {
				String urlEliminar = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(ControladorEstaciones.class)
								.darBajaBici(id, b.getContent().getCodigo()))
						.toUri()
						.toString();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		return emBicisDto;
	}

	@DeleteMapping("/{id}/bicis/{idBici}")
	public ResponseEntity<Void> darBajaBici(@PathVariable String id, @PathVariable String idBici) throws Exception {
		servEstaciones.darBajaBici(idBici, "Baja por usuario");
		// return a string informing the user that the bike was successfully removed
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/saludo")
	public String saludo() {
		return "Hola Mundo";
	}
}
