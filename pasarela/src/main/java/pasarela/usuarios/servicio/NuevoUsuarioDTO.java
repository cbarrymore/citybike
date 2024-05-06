package pasarela.usuarios.servicio;

public class NuevoUsuarioDTO
{
    private String id;
    private String username;
    private String nombre;
    private String acceso;
    private String codigo;
    private boolean oauth2;

    public NuevoUsuarioDTO()
    {

    }

    public NuevoUsuarioDTO(String id, String username, String nombre, String acceso, String codigo, boolean oauth2)
    {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.acceso = acceso;
        this.codigo = codigo;
        this.oauth2 = oauth2;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getAcceso()
    {
        return acceso;
    }

    public void setAcceso(String acceso)
    {
        this.acceso = acceso;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public boolean getOauth2()
    {
        return oauth2;
    }

    public void setOauth2(boolean oauth2)
    {
        this.oauth2 = oauth2;
    }

}
