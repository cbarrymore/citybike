package estaciones2.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones2.bici.dto.BiciDto;
import estaciones2.bici.modelo.Bici;
import estaciones2.estacion.dto.NuevaEstacionDto;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.estacion.servicio.IServicioEstaciones;
import estaciones2.repositorio.RepositorioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import estaciones2.estacion.dto.EstacionDto;

@RestController
@RequestMapping("/api/estaciones")
@Tag(name = "Estaciones", description = "Gestión de estaciones")
public class ControladorEstaciones {

	private IServicioEstaciones servEstaciones;

	@Autowired
	private PagedResourcesAssembler<EstacionDto> pagedResourcesAssemblerEstacionDto;

	@Autowired
	private PagedResourcesAssembler<BiciDto> pagedResourcesAssemblerBiciDto;

	@Autowired
	public ControladorEstaciones(IServicioEstaciones servEstaciones) {
		this.servEstaciones = servEstaciones;
	}

	// #region gestor
	@Operation(summary = "Crear una nueva estación", description = "Crea una nueva estación en el sistema")
	@PostMapping
	public ResponseEntity<Void> crearEstacion(@Validated @RequestBody NuevaEstacionDto estacion)
			throws RepositorioException {
		String id = servEstaciones.altaEstacion(estacion.getNombre(), estacion.getNumPuestos(), estacion.getDirPostal(),
				estacion.getLatitud(), estacion.getLongitud());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	@PostMapping("/{id}/bicis")
	@Operation(summary = "Dar de alta una bicicleta", description = "Da de alta una bicicleta de la estación")
	public ResponseEntity<Void> darAltaBici(@RequestParam String modelo, @PathVariable String idEstacion)
			throws Exception {
		String idBici = servEstaciones.altaBici(modelo, idEstacion);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/bicis/{idBici}")
	@Operation(summary = "Dar de baja una bicicleta", description = "Da de baja una bicicleta de la estación")
	@io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Bicicleta dada de baja"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Bicicleta no encontrada") })

	public ResponseEntity<Void> darBajaBicicleta(@PathVariable String id, @PathVariable String idBici)
			throws Exception {
		servEstaciones.darBajaBici(idBici, "Baja por usuario");
		// return a string informing the user that the bike was successfully removed
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/bicis")
	@Operation(summary = "Obtener las bicicletas de una estación", description = "Obtiene las bicicletas de una estación")
	public PagedModel<EntityModel<BiciDto>> getBicisEstacion(@PathVariable String id, @RequestParam int page,
			@RequestParam int size) throws Exception {

		Pageable paginacion = PageRequest.of(page, size);

		Page<Bici> bicis = servEstaciones.biciEstacionPaginado(id, paginacion);
		PagedModel<EntityModel<BiciDto>> emBicisDto = this.pagedResourcesAssemblerBiciDto
				.toModel(bicis.map(b -> BiciDto.deEntidad(b, id)));
		emBicisDto.forEach(b -> {
			try {
				String urlEliminar = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(ControladorEstaciones.class)
								.darBajaBicicleta(id, b.getContent().getCodigo()))
						.toUri()
						.toString();
				b.add(Link.of(urlEliminar, "delete"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		return emBicisDto;
	}

	// #endregion

	// #region all
	@GetMapping
	@Operation(summary = "Obtener las estaciones", description = "Obtiene las estaciones del sistema")
	public PagedModel<EntityModel<EstacionDto>> getEstaciones(@RequestParam int page, @RequestParam int size)
			throws Exception {
		Pageable paginacion = PageRequest.of(page, size);
		Page<Estacion> estaciones = servEstaciones.obtenerEstacionesPaginado(paginacion);

		return this.pagedResourcesAssemblerEstacionDto.toModel(estaciones.map(EstacionDto::deEntidad));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener una estación", description = "Obtiene una estación del sistema")
	public EstacionDto getEstacion(@PathVariable String id) throws Exception {
		Estacion estacion = servEstaciones.obtenerEstacion(id);
		return EstacionDto.deEntidad(estacion);
	}

	@GetMapping("/{id}/bicis/disponibles")
	@Operation(summary = "Obtener las bicicletas disponibles de una estación", description = "Obtiene las bicicletas disponibles de una estación")
	public PagedModel<EntityModel<BiciDto>> getBicisDisponiblesEstacion(@PathVariable String id, @RequestParam int page,
			@RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size);
		Page<Bici> bicis = servEstaciones.bicisEstacionLimitadoPaginado(id, paginacion);

		PagedModel<EntityModel<BiciDto>> emBicisDto = this.pagedResourcesAssemblerBiciDto
				.toModel(bicis.map(b -> BiciDto.deEntidad(b, id)));

		return emBicisDto;
	}

	@PutMapping("/{id}/bicis/{idBici}")
	@Operation(summary = "Estacionar una bicicleta", description = "Estaciona una bicicleta en la estación")
	public ResponseEntity<Void> estacionarBicicleta(@PathVariable String idEstacion, @PathVariable String idBici)
			throws Exception {
		servEstaciones.estacionarBici(idEstacion, idBici);
		return ResponseEntity.noContent().build();
	}
	// #endregion
}
