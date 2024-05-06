package pasarela.usuarios.servicio;

public class UsuarioDto {
	public String Id;
    public String Nombre;
    public String Rol;
    public String Acceso;
    public boolean Baja;
	
	public UsuarioDto(String Id, String Nombre, String Rol, String Acceso, boolean Baja)
    {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Rol = Rol;
        this.Acceso = Acceso;
        this.Baja = Baja;
    }
}
