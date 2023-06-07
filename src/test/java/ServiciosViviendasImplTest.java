import lombok.extern.log4j.Log4j2;
import org.example.common.m2Exception;
import org.example.dao.DaoViviendas;
import org.example.dao.DaoViviendasImpl;
import org.example.domain.Vivienda;
import org.example.service.ServiciosViviendasImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;




import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ServiciosViviendasImplTest {
    @InjectMocks
    private ServiciosViviendasImpl serviciosViviendas;
    @Mock
    private DaoViviendas daoViviendas;

    @Test
    public void testGetListaViviendas() throws m2Exception {

        List<Vivienda> listaViviendas = new ArrayList<>();

        listaViviendas.add(new Vivienda(1, "Calle sdfsf", 1, "Madrid", 100000, 50));
        listaViviendas.add(new Vivienda(2, "Calle das", 4, "Madrid", 300000, 50));
        listaViviendas.add(new Vivienda(3, "Calle lkjl", 5, "Madrid", 210000, 50));

        when(daoViviendas.getListaViviendas()).thenReturn(listaViviendas);
        List<Vivienda> respuesta = serviciosViviendas.getListaViviendas();

        assertThat(respuesta).isEqualTo(listaViviendas);
        assertNotNull(respuesta);
        assertEquals(3, respuesta.size());
    }
    @Test
    public void testGetListaViviendas3() throws m2Exception {
        List<Vivienda> listaViviendas = new ArrayList<>();

        listaViviendas.add(new Vivienda(1, "Calle sdfsf", 1, "Madrid", 100000, 50));
        listaViviendas.add(new Vivienda(2, "Calle das", 4, "Madrid", 300000, 50));
        listaViviendas.add(new Vivienda(3, "Calle lkjl", 5, "Madrid", 210000, 50));

        DaoViviendas daoViviendasSpy = Mockito.spy(DaoViviendasImpl.class);
        when(daoViviendasSpy.getListaViviendas()).thenReturn(listaViviendas);

        serviciosViviendas = new ServiciosViviendasImpl(daoViviendasSpy);
        List<Vivienda> respuesta = serviciosViviendas.getListaViviendas();

        assertThat(respuesta).isEqualTo(listaViviendas);
        assertNotNull(respuesta);
        assertEquals(3, respuesta.size());
    }
    @Test
    public void testIsEmptyViviendasList() throws m2Exception {
        Assertions.assertTrue(serviciosViviendas.isEmptyViviendasList());

        Vivienda vivienda = new Vivienda(1, "Calle A", 10, "Provincia A", 100000, 100);
        serviciosViviendas.addVivienda(vivienda);

        Assertions.assertFalse(serviciosViviendas.isEmptyViviendasList());
    }
    @Test
    public void testAddVivienda() throws m2Exception {
        Vivienda vivienda = new Vivienda(1, "Calle A", 10, "Provincia A", 100000, 100);
        vivienda.setM2(100);
        assertTrue(serviciosViviendas.addVivienda(vivienda));

        vivienda.setM2(-50); // Prueba de una vivienda con m2 negativo
        assertThrows(m2Exception.class, () -> serviciosViviendas.addVivienda(vivienda));
    }

    @Test
    public void testConsulta() throws m2Exception {
        List<Vivienda> listaViviendas = new ArrayList<>();

        Vivienda vivienda1 = new Vivienda(1, "Calle werwer", 10, "Provincia A", 100000, 100);
        vivienda1.setProvincia("Provincia A");
        vivienda1.setPrecio(100000);
        listaViviendas.add(vivienda1);

        Vivienda vivienda2 = new Vivienda(2, "Calle adsf", 10, "Provincia A", 100000, 100);
        vivienda2.setProvincia("Provincia A");
        vivienda2.setPrecio(200000);
        listaViviendas.add(vivienda2);

        Vivienda vivienda3 = new Vivienda(3, "Calle bahia", 13, "Madrid", 89000, 78);
        vivienda3.setProvincia("Provincia B");
        vivienda3.setPrecio(150000);
        listaViviendas.add(vivienda3);

        Vivienda vivienda4 = new Vivienda(4, "Calle aeronave", 23, "Madrid", 83222, 70);
        vivienda4.setProvincia("Provincia B");
        vivienda4.setPrecio(250000);
        listaViviendas.add(vivienda4);



        serviciosViviendas = new ServiciosViviendasImpl(daoViviendas);

        List<Vivienda> consulta1 = serviciosViviendas.consulta("Provincia A", 150000, 200000);
        assertEquals(1, consulta1.size());
        assertEquals(vivienda1, consulta1.get(0));

        List<Vivienda> consulta2 = serviciosViviendas.consulta("Provincia B", 200000, 300000);
        assertEquals(1, consulta2.size());
        assertEquals(vivienda4, consulta2.get(0));
    }

    @Test
    public void testActualizarm2() throws m2Exception {
        Vivienda vivienda = new Vivienda(1, "Calle A", 10, "Provincia A", 100000, 100);
        vivienda.setId(1);
        vivienda.setM2(100);

        serviciosViviendas.addVivienda(vivienda);

        assertTrue(serviciosViviendas.actualizarm2(1, 150));
        assertFalse(serviciosViviendas.actualizarm2(2, 200)); // Prueba de un id inexistente

        vivienda.setM2(-50); // Prueba de una actualización con m2 negativo
        assertThrows(m2Exception.class, () -> serviciosViviendas.actualizarm2(1, -50));
    }

    @Test
    public void testConsultaViviendas() throws m2Exception {
        List<Vivienda> listaViviendas = new ArrayList<>();

        Vivienda vivienda1 = new Vivienda(1, "Calle sdfs", 10, "Provincia A", 100000, 100);
        vivienda1.setCalle("Calle A");
        listaViviendas.add(vivienda1);

        Vivienda vivienda2 = new Vivienda(2, "Calle gfge", 10, "Provincia A", 100000, 100);
        vivienda2.setCalle("Calle B");
        listaViviendas.add(vivienda2);

        Vivienda vivienda3 = new Vivienda(3, "Calle casd", 10, "Madrid", 100000, 100);
        vivienda3.setCalle("Calle A");
        listaViviendas.add(vivienda3);

        Vivienda vivienda4 = new Vivienda(4, "Calle fdf", 10, "Provincia A", 100000, 100);
        vivienda4.setCalle("Calle C");
        listaViviendas.add(vivienda4);

        DaoViviendasImpl daoViviendas = new DaoViviendasImpl();
        daoViviendas.setListaViviendas(listaViviendas);

        serviciosViviendas = new ServiciosViviendasImpl(daoViviendas);

        List<Vivienda> consulta1 = serviciosViviendas.consultaViviendas("Calle A", true);
        consulta1.add(vivienda1);
        consulta1.add(vivienda2);
        assertEquals(2, consulta1.size());
        assertEquals(vivienda1, consulta1.get(0));
        assertEquals(vivienda3, consulta1.get(1));

        List<Vivienda> consulta2 = serviciosViviendas.consultaViviendas("Calle B", false);
        assertEquals(1, consulta2.size());
        assertEquals(vivienda2, consulta2.get(0));
    }

    @Test
    public void testGetListaViviendasProvincia() throws m2Exception {
        List<Vivienda> listaViviendas = new ArrayList<>();

        Vivienda vivienda1 = new Vivienda(1, "Calle sdfsdf", 10, "Provincia A", 100000, 100);
        vivienda1.setProvincia("Provincia A");
        listaViviendas.add(vivienda1);

        Vivienda vivienda2 = new Vivienda(2, "Calle swer", 10, "Provincia A", 100000, 100);
        vivienda2.setProvincia("Provincia B");
        listaViviendas.add(vivienda2);

        Vivienda vivienda3 = new Vivienda(3, "Calle fds", 10, "Provincia A", 100000, 100);
        vivienda3.setProvincia("Provincia A");
        listaViviendas.add(vivienda3);

        Vivienda vivienda4 = new Vivienda(4, "Calle gfda", 10, "Provincia A", 100000, 100);
        vivienda4.setProvincia("Provincia C");
        listaViviendas.add(vivienda4);

        DaoViviendasImpl daoViviendas = new DaoViviendasImpl();
        daoViviendas.setListaViviendas(listaViviendas);

        serviciosViviendas = new ServiciosViviendasImpl(daoViviendas);

        List<Vivienda> viviendasProvinciaA = serviciosViviendas.getListaViviendasProvincia("Provincia A");
        assertEquals(2, viviendasProvinciaA.size());
        assertEquals(vivienda1, viviendasProvinciaA.get(0));
        assertEquals(vivienda3, viviendasProvinciaA.get(1));

        List<Vivienda> viviendasProvinciaB = serviciosViviendas.getListaViviendasProvincia("Provincia B");
        assertEquals(1, viviendasProvinciaB.size());
        assertEquals(vivienda2, viviendasProvinciaB.get(0));
    }
}