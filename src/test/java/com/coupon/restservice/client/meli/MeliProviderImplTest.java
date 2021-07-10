package com.coupon.restservice.client.meli;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.coupon.restservice.client.meli.model.InfoItemMeli;
import com.coupon.restservice.client.meli.model.ItemMeli;
import com.coupon.restservice.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;

/**
 * @author <a href="mailto:laura.acosta@payu.com">Laura Acosta</a>
 * @since 1.0.0
 */
class MeliProviderImplTest {

    /**
     * Class under test
     */
    private MeliProviderImpl meliProvider;

    private MeliFeignClient meliFeignClient;

    @BeforeEach
    public void init() {

        meliFeignClient = mock(MeliFeignClient.class);
        meliProvider = new MeliProviderImpl(meliFeignClient);
    }

    @Test
    public void withGetInfoItems_shouldReturnItemListFromMeli() {

        List<ItemMeli> itemsMeli = getValidItemsFromMeli();
        when(meliFeignClient.getInfoItems(anyString(), anyString())).thenReturn(itemsMeli);

        Set<String> itemsIdToQuery = new HashSet<>();
        itemsIdToQuery.add("MLA811601010");
        itemsIdToQuery.add("MLA811601015");
        itemsIdToQuery.add("MLA811601016");


        Set<Item> infoItems = meliProvider.getInfoItems(itemsIdToQuery);

        assertEquals(3, infoItems.size());
    }

    private List<ItemMeli> getValidItemsFromMeli() {

        InfoItemMeli infoItem1 = new InfoItemMeli("Huawei", "MLA811601010", 58000.0);
        ItemMeli itemMeli1 = new ItemMeli(200, infoItem1);

        InfoItemMeli infoItem2 = new InfoItemMeli("Motorola", "MLA811601015", 48000.0);
        ItemMeli itemMeli2 = new ItemMeli(200, infoItem2);

        InfoItemMeli infoItem3 = new InfoItemMeli("Samsung", "MLA811601016", 68000.0);
        ItemMeli itemMeli3 = new ItemMeli(200, infoItem3);

        ItemMeli itemMeli4 = new ItemMeli(400, null);

        List<ItemMeli> itemMeliList = new ArrayList<>();
        itemMeliList.add(itemMeli1);
        itemMeliList.add(itemMeli2);
        itemMeliList.add(itemMeli3);
        itemMeliList.add(itemMeli4);

        return itemMeliList;

    }

}