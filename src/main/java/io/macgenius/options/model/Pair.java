package io.macgenius.options.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair implements Comparable<Pair> {
    private String name;
    private String value;

    public Pair(String name, Integer value) {
        this.name = name;
        this.value = value + "";
    }

    @Override
    public int compareTo(Pair o) {
        return name.compareTo(o.name);
    }
}
