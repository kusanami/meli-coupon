package com.coupon.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.coupon.restservice.client.meli.MeliProvider;
import com.coupon.restservice.model.Item;
import com.coupon.restservice.model.RequestCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
public class CouponServiceImplTest {

    /**
     * Class under test
     */
    private CouponServiceImpl couponService;

    /**
     * Class that doing request against Meli
     */
    private MeliProvider meliProvider;

    @BeforeEach
    public void init() {

        meliProvider = mock(MeliProvider.class);
        couponService = new CouponServiceImpl(meliProvider);
    }

    @Test
    public void whenGetItemsFromCouponAndReturnInformationFromMeli_shouldReturnIdItemsThatCanGet() {

        final RequestCoupon requestCoupon = createRequestCoupon(30000.0f);

        when(meliProvider.getInfoItems(requestCoupon.getItems())).thenReturn(getItemsValid());

        Set<String> idExpected = new HashSet<>();
        idExpected.add("MLA811601010");
        idExpected.add("MLA810645375");

        final Set<String> itemsInCoupon = couponService.getItemsFromCoupon(requestCoupon);

        assertEquals(2, itemsInCoupon.size());
        assertTrue(itemsInCoupon.containsAll(idExpected));
    }

    @Test
    public void whenGetItemsFromCouponAndReturnInformationFromMeli_shouldReturnSetEmptyWhenTheCouponNotRise() {

        final RequestCoupon requestCoupon = createRequestCoupon(10000.0f);

        when(meliProvider.getInfoItems(requestCoupon.getItems())).thenReturn(getItemsValid());

        final Set<String> itemsInCoupon = couponService.getItemsFromCoupon(requestCoupon);

        assertEquals(0, itemsInCoupon.size());
    }

    @Test
    public void whenGetItemsFromCouponAndNotReturnInformationFromMeli_shouldReturnSetEmptyWhenTheCouponNotRise() {

        final RequestCoupon requestCoupon = createRequestCoupon(10000.0f);

        when(meliProvider.getInfoItems(requestCoupon.getItems())).thenReturn(getItemsWithoutInformationValid());

        final Set<String> itemsInCoupon = couponService.getItemsFromCoupon(requestCoupon);

        assertEquals(0, itemsInCoupon.size());
    }

    private Set<Item> getItemsWithoutInformationValid() {

        Item item1 = new Item("MLA811601010", "Item1 Meli",  null);
        Item item2 = new Item("MLA810645375", null,  null);
        Item item3 = new Item("MLA811601014", "Item1 Meli",  null);

        Set<Item> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }

    private Set<Item> getItemsValid() {

        Item item1 = new Item("MLA811601010", "Item1 Meli",  12000.0);
        Item item2 = new Item("MLA810645375", "Item1 Meli",  15200.0);
        Item item3 = new Item("MLA811601014", "Item1 Meli",  36000.0);

        Set<Item> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }

    private RequestCoupon createRequestCoupon(Float amountCoupon) {

        Set<String> items = new HashSet<>();
        items.add("MLA811601010");
        items.add("MLA810645375");
        items.add("MLA811601014");

        return new RequestCoupon(items, amountCoupon);
    }
}
