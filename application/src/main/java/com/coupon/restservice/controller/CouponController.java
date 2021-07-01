package com.coupon.restservice.controller;

import java.util.Set;

import com.coupon.restservice.model.RequestCoupon;
import com.coupon.restservice.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/coupon")
@Slf4j
public class CouponController {

    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Set<String>> itemsFromCoupon(@RequestBody RequestCoupon requestCoupon) {

        log.info("list items [{}] and amount [{}]", requestCoupon);

        return new ResponseEntity(couponService.getItemsFromCoupon(requestCoupon), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return new ResponseEntity("Test", HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleValidationException(Exception exc) {

        return null;
    }
}
