using usuarios.modelo;

namespace usuarios.servicios
{
    public interface IServicioUsuarios
    {
        //gestor
        public string solicitudCodigo();
        public void darBajaUsuario(string idUsuario);
        public List<Usuario> GetUsuarios();
        //usuario
        public Dictionary<string,string> verificarUsuario(string idUsuario, string password);
        public Dictionary<string,string> verificarUsuarioOAuth2(string idUsuario);
    }

    public class ServicioUsuarios : IServicioUsuarios
    {
        public void darBajaUsuario(string idUsuario)
        {
            throw new NotImplementedException();
        }

        public List<Usuario> GetUsuarios()
        {
            throw new NotImplementedException();
        }

        public string solicitudCodigo()
        {
            throw new NotImplementedException();
        }

        public Dictionary<string, string> verificarUsuario(string idUsuario, string password)
        {
            throw new NotImplementedException();
        }

        public Dictionary<string, string> verificarUsuarioOAuth2(string idUsuario)
        {
            throw new NotImplementedException();
        }
    }
}