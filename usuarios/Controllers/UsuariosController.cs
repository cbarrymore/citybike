

using Microsoft.AspNetCore.Authentication;
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

        [HttpPost("/{idUsuario}/solicitud")]
        public ActionResult<string> solicitudCodigo(string idUsuario)
        {
            string codigo = _servicioUsuarios.solicitudCodigo(idUsuario);
            return Ok(codigo);
        }
        
        [HttpPost]
        public ActionResult<Usuario> darAlta(NuevoUsuarioDTO usuarioDTO)
        {
            string usuario = _servicioUsuarios.darAltaUsuario(usuarioDTO.Id, usuarioDTO.Nombre, usuarioDTO.Acceso, usuarioDTO.Codigo);
            return Ok(usuario);
        }

        [HttpGet("/verificar/{idUsuario}")]
        public ActionResult<Dictionary<string, string>> verificarUsuario(string idUsuario, string acceso)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuario(idUsuario, acceso);
            return Ok(claims);
        }

        [HttpGet("/verificar/OAuth2/{idUsuario}")]
        public ActionResult<Dictionary<string, string>> verificarUsuarioOAuth2(string idUsuario, string acceso)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuarioOAuth2(idUsuario, acceso);
            return Ok(claims);
        }



    }

    public class ManejadorGlobalErrores : ExceptionFilterAttribute
    {
        public override void OnException(ExceptionContext context)
        {
            base.OnException(context);
            if(context.Exception is ArgumentException || context.Exception is FormatException)
            {
                context.Result = new BadRequestResult();
            }

            else if(context.Exception is EntidadNoEncontradaException)
            {
                context.Result = new NotFoundResult();
            }
            
        }
    }
}