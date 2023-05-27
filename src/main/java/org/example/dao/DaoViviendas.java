package org.example.dao;

import org.example.domain.Vivienda;

import java.util.List;

public interface DaoViviendas {

    boolean addVivienda(Vivienda vivienda) ;

    List<Vivienda> consulta(String provincia, double precio1, double precio2) ;
    List<Vivienda> viviendasPorCalleNumero(String provincia) ;

    boolean actualizarm2(int id, double m2) ;

    List<Vivienda>listadoOrdenadoViviendasCalle(String calle,boolean ascendente) ;
    List<Vivienda> getListaViviendasProvincia(String provincia) ;

    void removeVivienda(Vivienda vivienda) ;
    public List<Vivienda> getListaViviendas();
    void setViviendas(List<Vivienda> viviendas);
    boolean isEmptyViviendasList() ;
}