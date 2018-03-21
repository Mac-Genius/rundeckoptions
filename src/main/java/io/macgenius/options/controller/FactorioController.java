package io.macgenius.options.controller;

import io.macgenius.options.model.Pair;
import io.macgenius.options.service.FactorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class FactorioController {
    private final FactorioService factorioService;

    @RequestMapping(path = "/factorio/versions", produces = "application/json", method = RequestMethod.GET)
    public List<Pair> getVersions() {
        return factorioService.getVersions();
    }
}
