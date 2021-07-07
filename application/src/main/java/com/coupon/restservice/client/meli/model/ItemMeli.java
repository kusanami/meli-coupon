package com.coupon.restservice.client.meli.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class ItemMeli {

    private int code;

    private InfoItemMeli body;
}
