using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using Microsoft.VisualBasic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;

namespace usuarios.modelo
{

    public class Usuario
    {
        [BsonId]
        public string Id{ get; set; }
        public string Username{ get; set; }
        public string Nombre{ get; set; }
        public string Rol{ get; set; }
        public string Acceso{ get; set; }
        public bool OAuth2{ get; set; }
        public bool Baja{ get; set; }

        public Usuario()
        {

        }

        public Usuario(string Id, string Username, string Nombre, string Rol, string Acceso, bool OAuth2, bool Baja)
        {
            this.Id = Id;
            this.Username = Username;
            this.Nombre = Nombre;
            this.Rol = Rol;
            this.Acceso = Acceso;
            this.OAuth2 = OAuth2;
            this.Baja = Baja;
        }

        public Usuario(string Id, string Username, string Nombre, string Rol, string Acceso, bool OAuth2)
        {
            this.Id = Id;
            this.Username = Username;
            this.Nombre = Nombre;
            this.Rol = Rol;
            this.Acceso = Acceso;
            this.OAuth2 = OAuth2;
            this.Baja = false;
        }

        public Dictionary<string, string> getClaims()
        {
            Dictionary<string, string> claims = new Dictionary<string, string>();
            claims.Add("id",Id);
            claims.Add("nombre",Nombre);
            claims.Add("rol",Rol);
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
        public DateTime Validez{ get; set; }
        public CodigoActivacion()
        {

        }

        public CodigoActivacion(string Codigo, string IdUsuario, bool Utilizado, DateTime Validez)
        {
            this.Codigo = Codigo;
            this.IdUsuario = IdUsuario;
            this.Utilizado = Utilizado;
            this.Validez = Validez;
        }

        public CodigoActivacion(string Codigo, string IdUsuario)
        {
            this.Codigo = Codigo;
            this.IdUsuario = IdUsuario;
            this.Utilizado = false;
            this.Validez = DateAndTime.Now.AddDays(7);
        }

        public CodigoActivacion( string IdUsuario)
        {
            this.IdUsuario = IdUsuario;
            this.Utilizado = false;
            this.Validez = DateAndTime.Now.AddDays(7);
        }

        public bool isValido()
        {
            return !Utilizado && (Validez.CompareTo(DateAndTime.Now) > 0);
        }

        public bool isCaducado()
        {
            return Validez.CompareTo(DateAndTime.Now) > 0;
        }

    }

    public class NuevoUsuarioDTO
    {
        public string Id{ get; set; }
        public string Username{ get; set; }
        public string Nombre{ get; set; }
        public string Codigo{ get; set; }
        public string Acceso{ get; set; }
        public bool OAuth2{ get; set; }
        public NuevoUsuarioDTO()
        {

        }

        public NuevoUsuarioDTO(string Id, string Username, string Nombre, string Codigo, string Acceso, bool OAuth2)
        {
            this.Id = Id;
            this.Username = Username;
            this.Nombre = Nombre;
            this.Codigo = Codigo;
            this.Acceso = Acceso;
            this.OAuth2 = OAuth2;
        }

    }

    public class UsuarioDTO
    {
        public string Id{ get; set; }
        public string Username{ get; set; }
        public string Rol{ get; set; }

        public UsuarioDTO(string Id, string Nombre, string Rol)
        {
            this.Id = Id;
            this.Username = Nombre;
            this.Rol = Rol;
        }

        public UsuarioDTO()
        {

        }

        public UsuarioDTO(Usuario usuario)
        {
            Id = usuario.Id;
            Username = usuario.Username;
            Rol = usuario.Rol;
        }

    }
}