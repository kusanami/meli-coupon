package com.coupon.restservice.service;

import java.util.Set;

import com.coupon.restservice.model.RequestCoupon;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
public interface CouponService {

    Set<String> getItemsFromCoupon(RequestCoupon requestCoupon);
}
