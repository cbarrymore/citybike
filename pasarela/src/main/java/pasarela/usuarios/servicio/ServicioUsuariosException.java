package pasarela.excepciones;

public class ServicioUsuariosException extends Exception
{
    public ServicioUsuariosException(String msg, Throwable causa) {
        super(msg, causa);
    }

    public ServicioUsuariosException(String msg) {
        super(msg);
    }
}
