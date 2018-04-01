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
import java.util.List;

@Service
@Log4j2
public class DigitalOceanServiceImpl implements DigitalOceanService {
    private final int PAGE_SIZE = 20;
    @Override
    @Cacheable("images")
    public PairList listImages(String accessName, String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        int page = 1;
        try {
            List<Image> images = client.getAvailableImages(page, PAGE_SIZE).getImages();
            while (images.size() > 0) {
                images.forEach(image -> {
                    if (image.getSlug() != null) {
                        list.add(new Pair(image.getName(), image.getSlug()));
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
        return list;
    }

    @Override
    @Cacheable("regions")
    public PairList listRegions(String accessName, String accessToken) {
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
        return list;
    }

    @Override
    @Cacheable("sizes")
    public PairList listSizes(String accessName, String accessToken) {
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
    public PairList listKeys(String accessName, String accessToken) {
        PairList list = new PairList();
        DigitalOceanClient client = new DigitalOceanClient("v2", accessToken);
        try {
            Keys keys = client.getAvailableKeys(1);
            List<Key> keyList = keys.getKeys();
            keyList.forEach(key -> list.add(new Pair(key.getName(), key.getId())));
        } catch (DigitalOceanException | RequestUnsuccessfulException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }


}
