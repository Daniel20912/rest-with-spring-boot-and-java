package com.danieloliveira.restwithspringbootandjava.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // recebe a classe de origem e a de destino para qual deve ser convertida
    public static<Origem, Destino> Destino parseObject(Origem origem, Class<Destino> destino) {
        return mapper.map(origem, destino);
    }

    // converte cada um dos objetos de origem no de destino
    public static<Origem, Destino> List<Destino> parseListObjects(List<Origem> origem, Class<Destino> destino) {
        List<Destino> destinationObjects = new ArrayList<Destino>();
        for (Origem o : origem){
            destinationObjects.add(mapper.map(o, destino));
        }

        return destinationObjects;

    }
}
