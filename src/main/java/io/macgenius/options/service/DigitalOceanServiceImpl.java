package io.macgenius.options.service;

import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;
import com.myjeeva.digitalocean.pojo.*;
import io.macgenius.options.model.Pair;
import io.macgenius.options.model.PairList;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j2
public class DigitalOceanServiceImpl implements DigitalOceanService {
    private final int PAGE_SIZE = 20;
    @Override
    @Cacheable("images")
    public PairList listImages(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        int page = 1;
        try {
            List<Image> images = client.getAvailableImages(page, PAGE_SIZE).getImages();
            while (images.size() > 0) {
                images.forEach(image -> {
                    if (image.getSlug() != null) {
                        list.add(new Pair(image.getDistribution() + " " + image.getName(), image.getSlug()));
                    } else {
                        list.add(new Pair(image.getName(), image.getId() + ""));
                    }
                });
                page++;
                images = client.getAvailableImages(page, PAGE_SIZE).getImages();
            }
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        return list;
    }

    @Override
    @Cacheable("regions")
    public PairList listRegions(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        try {
            System.out.println("test");
            Regions regions = client.getAvailableRegions(1);
            List<Region> regionList = regions.getRegions();
            regionList.forEach(region -> {
                list.add(new Pair(region.getName(), region.getSlug()));
            });
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        return list;
    }

    @Override
    @Cacheable("sizes")
    public PairList listSizes(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        DecimalFormat format = new DecimalFormat("$#,##0.00#");
        try {
            Sizes sizes = client.getAvailableSizes(1);
            List<Size> sizeList = sizes.getSizes();
            sizeList.forEach(size -> {
                String builder = "Mem: " + size.getMemorySizeInMb() + "MB; " +
                        "vCPUs: " + size.getVirutalCpuCount() + "; " +
                        "SSD Size: " + size.getDiskSize() + "GB; " +
                        "Transfer: " + size.getTransfer() + "; " +
                        "Price: " + format.format(size.getPriceMonthly().doubleValue()) + "/mo or "
                        + format.format(size.getPriceHourly().doubleValue()) + "/hr";
                list.add(new Pair(builder, size.getSlug()));
            });
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Cacheable("keys")
    public PairList listKeys(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        try {
            Keys keys = client.getAvailableKeys(1);
            List<Key> keyList = keys.getKeys();
            keyList.forEach(key -> list.add(new Pair(key.getName(), key.getId())));
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            log.error(e.getMessage(), e);
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public PairList listDroplets(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        int page = 1;
        try {
            Droplets droplets = client.getAvailableDroplets(page, PAGE_SIZE);
            List<Droplet> dropletList = droplets.getDroplets();
            while (dropletList.size() > 0) {
                dropletList.forEach(droplet -> {
                    list.add(new Pair(droplet.getName(), droplet.getId()));
                });
                page++;
                droplets = client.getAvailableDroplets(page, PAGE_SIZE);
                dropletList = droplets.getDroplets();
            }
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public PairList listTags(String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        int page = 1;
        try {
            Tags tags = client.getAvailableTags(page, PAGE_SIZE);
            List<Tag> tagList = tags.getTags();
            while (tagList.size() > 0) {
                tagList.forEach(tag -> {
                    list.add(new Pair(tag.getName(), tag.getName()));
                });
                page++;
                tags = client.getAvailableTags(page, PAGE_SIZE);
                tagList = tags.getTags();
            }
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }
}
