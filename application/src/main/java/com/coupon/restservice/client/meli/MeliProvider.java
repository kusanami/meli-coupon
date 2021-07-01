package com.coupon.restservice.client.meli;

import java.util.Set;

import com.coupon.restservice.model.Item;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
public interface MeliProvider {

    Set<Item> doQuery(Set<String> items);
}
