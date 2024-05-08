package aadd.pasarela;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pasarela.excepciones.ServicioUsuariosException;
import pasarela.usuarios.servicio.IServicioUsuarios;
import pasarela.usuarios.servicio.ServicioUsuariosRetrofit;



public class ServicioUsuariosTest {
    private ServicioUsuariosRetrofit servicioUsuarios;

    @BeforeEach
    public void setUp() {
    	servicioUsuarios = new ServicioUsuariosRetrofit();
    }
}