package com.coupon.restservice.client.meli;

import static com.coupon.restservice.client.meli.util.MapperItemMeli.itemMeliMapperToItem;
import static java.lang.String.join;

import java.util.Set;
import java.util.stream.Collectors;

import com.coupon.restservice.client.meli.util.MapperItemMeli;
import com.coupon.restservice.model.Item;
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

    @Override
    public Set<Item> getInfoItems(Set<String> items) {

        return meliFeignClient.getInfoItems(join(",",items), ITEM_ATTRIBUTES)
                .stream()
                .map(itemMeli -> itemMeliMapperToItem(itemMeli))
                .collect(Collectors.toSet());
    }
}
