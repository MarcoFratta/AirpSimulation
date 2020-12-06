package org.VoituLex.model;

import org.VoituLex.model.Bot;

import java.util.ArrayList;
import java.util.List;


@FunctionalInterface
public interface SortingMethod {
    public void assignTicket(List<? extends Bot> bots);
}
