package com.coupon.restservice.client.meli;

import static com.coupon.restservice.client.meli.helper.MapperItemMeli.itemMeliMapperToItem;
import static java.lang.String.join;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.coupon.restservice.model.Item;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@Service
public class MeliProviderImpl implements MeliProvider {

    private final String ITEM_ATTRIBUTES = "id,price,title";

    private MeliFeignClient meliFeignClient;

    public MeliProviderImpl(MeliFeignClient meliFeignClient) {
        this.meliFeignClient = meliFeignClient;
    }

    @CircuitBreaker(name = "Meli", fallbackMethod = "doFallback")
    @Override
    public Set<Item> getInfoItems(final Set<String> items) {

        return meliFeignClient.getInfoItems(join(",",items), ITEM_ATTRIBUTES)
                .stream()
                .filter(itemMeli -> itemMeli.getCode() == 200)
                .map(itemMeli -> itemMeliMapperToItem(itemMeli))
                .collect(Collectors.toSet());
    }

    private Set<Item> doFallback(final Set<String> items,
                                 final Throwable throwable) {

        return new HashSet<>();
    }
}
