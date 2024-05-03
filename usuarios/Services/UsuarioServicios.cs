using Repositorio;
using usuarios.Exceptions;
using usuarios.modelo;

namespace usuarios.servicios
{
    public interface IServicioUsuarios
    {
        //gestor
        public string solicitudCodigo(string idUsuario);
        public void darBajaUsuario(string idUsuario);
        public List<Usuario> GetUsuarios();
        //usuario
        public string darAltaUsuario(string idUsuario, string nombre, string acceso, string codigo);
        public Dictionary<string,string> verificarUsuario(string idUsuario, string password);
        public Dictionary<string,string> verificarUsuarioOAuth2(string idUsuario, string acceso);
    }

    public class ServicioUsuarios : IServicioUsuarios
    {

        private Repositorio<Usuario, string> repositorioUsuarios;
        private Repositorio<CodigoActivacion, string> repositorioCodigos;

        public ServicioUsuarios(Repositorio<Usuario, string> repositorioUsuarios, Repositorio<CodigoActivacion, string> repositorioCodigos)
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

        public string darAltaUsuario(string idUsuario, string nombre, string acceso, string codigo)
        {
            if(repositorioUsuarios.GetById(idUsuario) != null)
                throw new InvalidOperationException("El usuario ya se ha dado de alta");
            CodigoActivacion codigoActivacion = repositorioCodigos.GetById(codigo);
            if(codigoActivacion == null || !codigoActivacion.isValido())
                throw new Exception("El codigo de activación no es válido");
            Usuario usuario = new Usuario(idUsuario,nombre,"Usuario",acceso);
            repositorioUsuarios.Add(usuario);
            codigoActivacion.Utilizado = true;
            repositorioCodigos.Update(codigoActivacion);
            return usuario.Id;
        }

        public Dictionary<string, string> verificarUsuario(string idUsuario, string password)
        {
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);
            if(usuario == null)
                throw new EntidadNoEncontradaException("El usuario no existe");
            if(usuario.Baja)
                throw new InvalidOperationException("El usuario esta dado de baja");
            if(!usuario.Acceso.Equals(password))
                 throw new InvalidDataException("Contraseña incorrecta");
            return usuario.getClaims();
        }

        public Dictionary<string, string> verificarUsuarioOAuth2(string idUsuario, string acceso)
        {
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);
            if(usuario == null)
                throw new EntidadNoEncontradaException("El usuario no existe");
            if(usuario.Baja)
                throw new InvalidOperationException("El usuario esta dado de baja");
            if(!usuario.Acceso.Equals(acceso))
                 throw new InvalidDataException("OAuth incorrecto");
            return usuario.getClaims();
        }
    }
}