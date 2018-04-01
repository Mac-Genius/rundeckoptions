package io.macgenius.options.service;

import io.macgenius.options.model.PairList;

public interface DigitalOceanService {
    PairList listImages(String accessName, String accessToken);

    PairList listRegions(String accessName, String accessToken);

    PairList listSizes(String accessName, String accessToken);

    PairList listKeys(String accessName, String accessToken);
}
