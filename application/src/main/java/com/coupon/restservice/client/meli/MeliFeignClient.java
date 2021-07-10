package com.coupon.restservice.client.meli;

import java.util.List;
import java.util.Set;

import com.coupon.restservice.client.meli.model.ItemMeli;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@FeignClient(name = "meliClient", url = "${meli.url}")
public interface MeliFeignClient {

    @GetMapping("items")
    List<ItemMeli> getInfoItems(@RequestParam String ids, @RequestParam String attributes);

}
