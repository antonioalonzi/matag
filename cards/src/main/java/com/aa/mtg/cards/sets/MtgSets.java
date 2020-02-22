package com.aa.mtg.cards.sets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.aa.mtg.cards.CardsConfiguration.getResourcesPath;

@Component
public class MtgSets {
    private static final String SETS_PATH = getResourcesPath() + "/sets";

    private Map<String, MtgSet> SETS = new LinkedHashMap<>();

    public MtgSets(ObjectMapper objectMapper) throws Exception {
        String[] sets = new File(SETS_PATH).list();
        Objects.requireNonNull(sets);
        for (String set : sets) {
            MtgSet mtgSet = objectMapper.readValue(new File(SETS_PATH + "/" + set), MtgSet.class);
            SETS.put(mtgSet.getCode(), mtgSet);
        }
    }

    public Map<String, MtgSet> getSets() {
        return SETS;
    }

    public MtgSet getSet(String code) {
        return SETS.get(code);
    }
}
