

using System.Collections;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.Extensions.Primitives;
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
        public ActionResult<OkObjectResult> solicitudCodigo(string idUsuario)
        {
            string codigo = _servicioUsuarios.solicitudCodigo(idUsuario);
            return Ok(codigo);
        }
        
        [HttpPost]
        public ActionResult<OkObjectResult> darAlta(NuevoUsuarioDTO usuarioDTO)
        {
            bool accion = usuarioDTO.OAuth2 != null;
            string acceso;
            if(usuarioDTO.OAuth2 != null)
                acceso = usuarioDTO.OAuth2;
            else
                acceso = usuarioDTO.Acceso;
            string usuario = _servicioUsuarios.darAltaUsuario(usuarioDTO.Id, usuarioDTO.Username, usuarioDTO.Nombre, acceso, usuarioDTO.Codigo, accion);
            return Ok(usuario);
        }

        [HttpGet("verificar/{idUsuario}")]
        public ActionResult<OkObjectResult> verificarUsuario(string idUsuario, string acceso)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuario(idUsuario, acceso);
            return Ok(claims);
        }

        [HttpGet("verificar/OAuth2/{idUsuario}")]
        public ActionResult<OkObjectResult> verificarUsuarioOAuth2(string oauth2)
        {
            Dictionary<string, string> claims = _servicioUsuarios.verificarUsuarioOAuth2(oauth2);
            return Ok(claims);
        }

        [HttpGet]
        public ActionResult<OkObjectResult> getUsuarios()
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
            if(context.Exception is ArgumentException || context.Exception is FormatException)
            {
                Console.WriteLine(context.Exception.Message);
                context.Result = new BadRequestResult();
            }

            else if(context.Exception is EntidadNoEncontradaException)
            {
                Console.WriteLine(context.Exception.Message);
                context.Result = new NotFoundResult();
            }

            else if(context.Exception is InvalidOperationException)
            {
                //Algo
                Console.WriteLine(context.Exception.Message);
                context.Result = new BadRequestResult();
            }
            
        }
    }
}