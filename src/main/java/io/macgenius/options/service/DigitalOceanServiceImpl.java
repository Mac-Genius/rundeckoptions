package io.macgenius.options.service;

import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;
import com.myjeeva.digitalocean.pojo.*;
import io.macgenius.options.model.Pair;
import io.macgenius.options.model.PairList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
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
                    list.add(new Pair(image.getName(), image.getSlug()));
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
}
