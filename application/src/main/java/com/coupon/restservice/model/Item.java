package com.coupon.restservice.model;

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
public class Item {

    private String id;

    private String title;

    private Double price;

}
