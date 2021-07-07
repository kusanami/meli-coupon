package com.coupon.restservice.service;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

import com.coupon.restservice.client.meli.MeliProvider;
import com.coupon.restservice.model.Item;
import com.coupon.restservice.model.RequestCoupon;
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

        SortedSet<String> itemsInCoupon = new TreeSet<>();
        AtomicReference<Double> priceItemsInCoupon = new AtomicReference<>(0.0);

        getInformationFromMeli(requestCoupon.getItems())
                .stream()
                .sorted(Comparator.comparingDouble(Item::getPrice))
                .forEach(item -> {
                            priceItemsInCoupon.updateAndGet(v -> v + item.getPrice());
                            if (priceItemsInCoupon.get() <= requestCoupon.getAmount()) {
                                itemsInCoupon.add(item.getId());
                            }
                        }
                );

        return itemsInCoupon;
    }

    private Set<Item> getInformationFromMeli(Set<String> items) {

        return meliProvider.getInfoItems(items);
    }
}
