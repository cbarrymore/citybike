using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;

namespace usuarios.modelo
{

    public abstract class Usuario
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id{ get; set; }
        public string Nombre{ get; set; }
        public string Rol{ get; set; }

        public Dictionary<string, string> getClaims()
        {
            Dictionary<string, string> claims = new Dictionary<string, string>();
            claims.Add("Id",Id);
            claims.Add("Nombre",Nombre);
            claims.Add("Rol",Rol);
            return claims;
        }
    }
    
    class UsuarioOAuth2 : Usuario
    {
        public UsuarioOAuth2()
        {

        }

        public UsuarioOAuth2(string id, string nombre, string rol)
        {
            Id = id;
            Nombre = nombre;
            Rol = rol;
        }
    }

    class UsuarioPassword : Usuario
    {
        public UsuarioPassword()
        {

        }

        public UsuarioPassword(string id, string nombre, string rol, string password)
        {
            Id = id;
            Nombre = nombre;
            Rol = rol;
            Password = password;
        }
        public string Password{ get; set; }
    }

}