using Repositorio;
using usuarios.Exceptions;
using usuarios.modelo;
using usuarios.Repositorio;

namespace usuarios.servicios
{
    public interface IServicioUsuarios
    {
        //gestor
        public string solicitudCodigo(string idUsuario);
        public void darBajaUsuario(string idUsuario);
        public List<Usuario> GetUsuarios();
        //usuario
        public string darAltaUsuario(string idUsuario, string username, string nombre, string acceso, string codigo, bool oauth2);
        public Dictionary<string,string> verificarUsuario(string username, string password);
        public Dictionary<string,string> verificarUsuarioOAuth2(string oauth2);
    }

    public class ServicioUsuarios : IServicioUsuarios
    {

        private static string ROL_USUARIO = "Usuario";
        private RepositorioUsuarios repositorioUsuarios;
        private Repositorio<CodigoActivacion, string> repositorioCodigos;

        public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios, Repositorio<CodigoActivacion, string> repositorioCodigos)
        {
            this.repositorioUsuarios = repositorioUsuarios;
            this.repositorioCodigos = repositorioCodigos;
        }

        public void darBajaUsuario(string idUsuario)
        {
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);
            if(usuario == null)
                throw new EntidadNoEncontradaException("El usuario no existe");
            usuario.Baja = true;
            repositorioUsuarios.Update(usuario);
        }

        public List<Usuario> GetUsuarios()
        {
            return repositorioUsuarios.GetAll();
        }

        public string solicitudCodigo(string idUsuario)
        {
            if(repositorioUsuarios.GetById(idUsuario) != null)
                throw new InvalidOperationException("El usuario ya está dado de alta");
            string codigo = idUsuario;
            CodigoActivacion codigoActivacion = new CodigoActivacion(codigo,idUsuario);
            repositorioCodigos.Add(codigoActivacion);
            return codigoActivacion.Codigo;
        }

        public string darAltaUsuario(string idUsuario, string username, string nombre, string acceso, string codigo, bool oauth2)
        {
            if(repositorioUsuarios.GetById(idUsuario) != null)
                throw new InvalidOperationException("El usuario ya se ha dado de alta");
            if(repositorioUsuarios.GetById(username) != null)
                throw new InvalidOperationException("Nombre de usuario no disponible");
            CodigoActivacion codigoActivacion = repositorioCodigos.GetById(codigo);
            if(codigoActivacion == null || !codigoActivacion.isValido())
                throw new Exception("El codigo de activación no es válido");
            Usuario usuario = new Usuario(idUsuario,username,nombre,ROL_USUARIO,acceso, oauth2);
            repositorioUsuarios.Add(usuario);
            codigoActivacion.Utilizado = true;
            repositorioCodigos.Update(codigoActivacion);
            return usuario.Id;
        }

        public Dictionary<string, string> verificarUsuario(string username, string password)
        {
            Usuario usuario = repositorioUsuarios.GetByUsername(username);
            if(usuario == null)
                throw new EntidadNoEncontradaException("El usuario no existe");
            if(usuario.Baja)
                throw new InvalidOperationException("El usuario esta dado de baja");
            if(!usuario.Acceso.Equals(password))
                 throw new InvalidDataException("Contraseña incorrecta");
            return usuario.getClaims();
        }

        public Dictionary<string, string> verificarUsuarioOAuth2(string oauth2)
        {
            Usuario usuario = repositorioUsuarios.GetByOAuht2(oauth2);
            if(usuario == null)
                throw new EntidadNoEncontradaException("El usuario no existe");
            if(usuario.Baja)
                throw new InvalidOperationException("El usuario esta dado de baja");
            return usuario.getClaims();
        }
    }
}