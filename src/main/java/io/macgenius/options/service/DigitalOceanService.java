package io.macgenius.options.service;

import io.macgenius.options.model.PairList;

public interface DigitalOceanService {
    PairList listImages(String accessToken);

    PairList listRegions(String accessToken);

    PairList listSizes(String accessToken);

    PairList listKeys(String accessToken);

    PairList listDroplets(String accessToken);

    PairList listTags(String accessToken);
}
