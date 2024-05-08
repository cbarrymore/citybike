package pasarela.usuarios.dto;

public class NuevoUsuarioDTO
{
    private String Id;
    private String Username;
    private String Nombre;
    private String Acceso;
    private String Codigo;
    private boolean OAuth2;

    public NuevoUsuarioDTO()
    {

    }

    public NuevoUsuarioDTO(String id, String username, String nombre, String acceso, String codigo, boolean oauth2)
    {
        this.Id = id;
        this.Username = username;
        this.Nombre = nombre;
        this.Acceso = acceso;
        this.Codigo = codigo;
        this.OAuth2 = oauth2;
    }

    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        this.Id = id;
    }

    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String username)
    {
        this.Username = username;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }

    public String getAcceso()
    {
        return Acceso;
    }

    public void setAcceso(String acceso)
    {
        this.Acceso = acceso;
    }

    public String getCodigo()
    {
        return Codigo;
    }

    public void setCodigo(String codigo)
    {
        this.Codigo = codigo;
    }

    public boolean getOauth2()
    {
        return OAuth2;
    }

    public void setOauth2(boolean oauth2)
    {
        this.OAuth2 = oauth2;
    }

}
