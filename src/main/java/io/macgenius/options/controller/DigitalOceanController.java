package io.macgenius.options.controller;

import io.macgenius.options.model.PairList;
import io.macgenius.options.service.DigitalOceanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DigitalOceanController {
    private final DigitalOceanService digitalOceanService;

    @RequestMapping(path = "/digitalocean/images", produces = "application/json", method = RequestMethod.GET)
    public PairList listImages(@RequestParam("access_name") String accessName, @RequestParam("access_token") String accessToken) {
        return digitalOceanService.listImages(accessName, accessToken);
    }

    @RequestMapping(path = "/digitalocean/regions", produces = "application/json", method = RequestMethod.GET)
    public PairList listRegions(@RequestParam("access_name") String accessName, @RequestParam("access_token") String accessToken) {
        return digitalOceanService.listRegions(accessName, accessToken);
    }

    @RequestMapping(path = "/digitalocean/sizes", produces = "application/json", method = RequestMethod.GET)
    public PairList listSizes(@RequestParam("access_name") String accessName, @RequestParam("access_token") String accessToken) {
        return digitalOceanService.listSizes(accessName, accessToken);
    }

    @RequestMapping(path = "/digitalocean/keys", produces = "application/json", method = RequestMethod.GET)
    public PairList listKeys(@RequestParam("access_name") String accessName, @RequestParam("access_token") String accessToken) {
        return digitalOceanService.listKeys(accessName, accessToken);
    }
}
