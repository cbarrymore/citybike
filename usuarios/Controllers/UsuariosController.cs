

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using usuarios.Exceptions;
using usuarios.modelo;
using usuarios.servicios;

namespace usuarios.Controllers
{
    [Route("api/usuarios")]
    [ApiController]
    public class UsuariosController : ControllerBase
    {
        private readonly IServicioUsuarios _servicioUsuarios;

        public UsuariosController(IServicioUsuarios servicioUsuarios)
        {
            _servicioUsuarios = servicioUsuarios;
        }

        [HttpDelete("{idUsuario}")]
        public IActionResult darBajaUsuario(string idUsuario)
        {
            _servicioUsuarios.darBajaUsuario(idUsuario);
            return NoContent();
        }

        [HttpPost("solicitud/{idUsuario}")]
        public ActionResult<string> solicitudCodigo(string idUsuario)
        {
            string codigo = _servicioUsuarios.solicitudCodigo(idUsuario);
            return Ok(codigo);
        }
        
        [HttpPost]
        public IActionResult darAlta(NuevoUsuarioDTO usuarioDTO)
        { 
            string usuario = _servicioUsuarios.darAltaUsuario(usuarioDTO.Id, usuarioDTO.Username, usuarioDTO.Nombre, usuarioDTO.Acceso, usuarioDTO.Codigo, usuarioDTO.OAuth2);
            return NoContent();
        }

        [HttpGet("verificar/{username}/{password}")]
        public ActionResult<Dictionary<string, string>> verificarUsuario(string username, string password)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuario(username, password);
            return Ok(claims);
        }

        [HttpGet("verificar/OAuth2/{idOauth2}")]
        public ActionResult<Dictionary<string, string>> verificarUsuarioOAuth2(string idOauth2)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuarioOAuth2(idOauth2);
            return Ok(claims);
        }

        [HttpGet]
        public ActionResult<IEnumerable<UsuarioDTO>> getUsuarios()
        {
            List<Usuario> lista = _servicioUsuarios.GetUsuarios();
            List<UsuarioDTO> nueva = new List<UsuarioDTO>();
            for(int i =0; i<lista.Count;i++)
                nueva.Add(new UsuarioDTO(lista.ElementAt(i)));
            return Ok(nueva);
        }


    }

    public class ManejadorGlobalErrores : ExceptionFilterAttribute
    {
        public override void OnException(ExceptionContext context)
        {
            base.OnException(context);
            if(context.Exception is ArgumentException || context.Exception is FormatException || context.Exception is InvalidOperationException)
            {
                Console.WriteLine(context.Exception.Message);
                context.Result = new BadRequestObjectResult(context.Exception.Message);
            }

            else if(context.Exception is EntidadNoEncontradaException)
            {
                Console.WriteLine(context.Exception.Message);
                context.Result = new NotFoundObjectResult(context.Exception.Message);
            }
            
        }
    }
}