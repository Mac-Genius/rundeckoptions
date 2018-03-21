package io.macgenius.options.model;

import java.util.ArrayList;
import java.util.Collection;

public class PairList extends ArrayList<Pair> {
    public PairList(int initialCapacity) {
        super(initialCapacity);
    }

    public PairList() {
    }

    public PairList(Collection<? extends Pair> c) {
        super(c);
    }
}
