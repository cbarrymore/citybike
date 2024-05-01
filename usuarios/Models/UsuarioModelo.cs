using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;

namespace usuarios.modelo
{

    public class Usuario
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id{ get; set; }
        public string Nombre{ get; set; }
        public string Rol{ get; set; }
        public string Acceso{ get; set; }
        public bool Baja{ get; set; }

        public Usuario()
        {

        }

        public Usuario(string Id, string Nombre, string Rol, string Acceso, bool Baja)
        {
            this.Id = Id;
            this.Nombre = Nombre;
            this.Rol = Rol;
            this.Acceso = Acceso;
            this.Baja = Baja;
        }

        public Usuario(string Id, string Nombre, string Rol, string Acceso)
        {
            this.Id = Id;
            this.Nombre = Nombre;
            this.Rol = Rol;
            this.Acceso = Acceso;
            this.Baja = false;
        }

        public Dictionary<string, string> getClaims()
        {
            Dictionary<string, string> claims = new Dictionary<string, string>();
            claims.Add("Id",Id);
            claims.Add("Nombre",Nombre);
            claims.Add("Rol",Rol);
            return claims;
        }
    }

    public class CodigoActivacion
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Codigo{ get; set; }
        public string IdUsuario{ get; set; }
        public bool Utilizado{ get; set; }

        public CodigoActivacion()
        {

        }

        public CodigoActivacion(string Codigo, string IdUsuario, bool Utilizado)
        {
            this.Codigo = Codigo;
            this.IdUsuario = IdUsuario;
            this.Utilizado = Utilizado;
        }

        public CodigoActivacion(string Codigo, string IdUsuario)
        {
            this.Codigo = Codigo;
            this.IdUsuario = IdUsuario;
            this.Utilizado = false;
        }

    }

    public class NuevoUsuarioDTO
    {
        public string Id{ get; set; }
        public string Nombre{ get; set; }
        public string Acceso{ get; set; }
        public string Codigo{ get; set; }
        public NuevoUsuarioDTO()
        {

        }

        public NuevoUsuarioDTO(string Id, string Nombre, string Acceso, string Codigo)
        {
            this.Id = Id;
            this.Nombre = Nombre;
            this.Acceso = Acceso;
            this.Codigo = Codigo;
        }

    }
}