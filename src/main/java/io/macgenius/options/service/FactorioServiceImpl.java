package io.macgenius.options.service;

import io.macgenius.options.model.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactorioServiceImpl implements FactorioService {
    @Override
    public List<Pair> getVersions() {
        ArrayList<Pair> pairs = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://lua-api.factorio.com/").get();
            Elements elements = doc.select("body a");
            elements.forEach(element -> {
                if (!element.text().equals("Latest version")) {
                    pairs.add(new Pair(element.text(), element.text()));
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return pairs;
    }
}
