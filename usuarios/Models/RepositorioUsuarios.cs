using MongoDB.Driver;
using Repositorio;
using usuarios.modelo;

namespace usuarios.Repositorio
{

    public interface RepositorioUsuarios : Repositorio<Usuario, string>
    {
        public Usuario GetByUsername(string username);
        public Usuario GetByOAuht2(string oauth2);
    }

    public class RepositorioUsuariosMongoDB : RepositorioUsuarios
    {
        public readonly IMongoCollection<Usuario> usuarios;

        public RepositorioUsuariosMongoDB()
        {
            var database = new MongoClient("mongodb+srv://cbarrymore:arso@cluster0.zwz1o7s.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")
            .GetDatabase("usuarios");
            usuarios = database.GetCollection<Usuario>("usuarios");
        }

        public string Add(Usuario entity)
        {
            usuarios.InsertOne(entity);

            return entity.Id;
        }

        public void Delete(Usuario entity)
        {
            usuarios.DeleteOne(usuario => usuario.Id==entity.Id);
        }

        public List<Usuario> GetAll()
        {
            return usuarios.Find(_ => true).ToList();
        }

        public Usuario GetById(string id)
        {
            return usuarios
                .Find(usuario => usuario.Id == id)
                .FirstOrDefault();
        }

        public Usuario GetByUsername(string username)
        {
            return usuarios
                .Find(usuario => usuario.Username == username)
                .FirstOrDefault();
        }

        public Usuario GetByOAuht2(string oauth2)
        {
            return usuarios
                .Find(usuario =>  usuario.OAuth2 == true && usuario.Acceso == oauth2) 
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todos =  usuarios.Find(_ => true).ToList();
            return todos.Select(p => p.Id).ToList();
        }

        public void Update(Usuario entity)
        {
            usuarios.ReplaceOne(usuario => usuario.Id==entity.Id, entity);
        }
    }
    
    public class RepositorioCodigosActivacionMongoDB : Repositorio<CodigoActivacion, string>
    {
        public readonly IMongoCollection<CodigoActivacion> codigos;

        public RepositorioCodigosActivacionMongoDB()
        {
            var database = new MongoClient("mongodb+srv://cbarrymore:arso@cluster0.zwz1o7s.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")
            .GetDatabase("usuarios");
            codigos = database.GetCollection<CodigoActivacion>("codigosactivacion");
        }

        public string Add(CodigoActivacion entity)
        {
            codigos.InsertOne(entity);

            return entity.Codigo;
        }

        public void Delete(CodigoActivacion entity)
        {
            codigos.DeleteOne(codigo => codigo.Codigo == entity.Codigo);
        }

        public List<CodigoActivacion> GetAll()
        {
            return codigos.Find(_ => true).ToList();
        }

        public CodigoActivacion GetById(string id)
        {
            return codigos
                .Find(codigo => codigo.Codigo == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todos =  codigos.Find(_ => true).ToList();
            return todos.Select(p => p.Codigo).ToList();
        }

        public void Update(CodigoActivacion entity)
        {
            codigos.ReplaceOne(codigo => codigo.Codigo == entity.Codigo, entity);
        }
    }

}