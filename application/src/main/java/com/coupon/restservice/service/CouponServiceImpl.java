package com.coupon.restservice.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.coupon.restservice.client.meli.MeliProvider;
import com.coupon.restservice.model.Item;
import com.coupon.restservice.model.RequestCoupon;
import com.sun.tools.javac.jvm.Items;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    private MeliProvider meliProvider;

    public CouponServiceImpl(MeliProvider meliProvider) {
        this.meliProvider = meliProvider;
    }


    @Override
    public Set<String> getItemsFromCoupon(RequestCoupon requestCoupon) {

        Set<Item> items = getInformationFromMeli(requestCoupon.getItems());


        Set<String> set = new TreeSet<>();
        set.add("10");
        set.add("20");
        set.add("7");
        set.add("4");
        log.info("Set that return {}", set);
        return set;
    }

    private Set<Item> getInformationFromMeli(Set<String> items) {

        return meliProvider.doQuery(items);
    }
}
