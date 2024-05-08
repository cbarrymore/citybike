package aadd.pasarela;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pasarela.usuarios.servicio.IServicioUsuarios;
import pasarela.usuarios.servicio.ServicioUsuariosException;
import pasarela.usuarios.servicio.ServicioUsuariosRetrofit;



public class ServicioUsuariosTest {
    private ServicioUsuariosRetrofit servicioUsuarios;

    @BeforeEach
    public void setUp() {
    	servicioUsuarios = new ServicioUsuariosRetrofit();
    }
    
    
    @Test
    public void testVerificarUsuarioOauth2() throws ServicioUsuariosException, IOException {
    	Map<String,String> claims = servicioUsuarios.verificarUsuario("hola", "hola");
    	System.out.println(claims);
    }
}