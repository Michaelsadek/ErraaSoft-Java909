package service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Item;

public class ItemUtilService {

    private List<Item> items = new ArrayList<>();

  
    public List<Item> getItemsByIdsOneAndTwo() {
        return items.stream()
                .filter(item -> item.getId() == 1 || item.getId() == 2)
                .collect(Collectors.toList());
    }

    public List<Item> getItemsNameContainsI() {
        return items.stream()
                .filter(item -> item.getName() != null &&
                        item.getName().toLowerCase().contains("i"))
                .collect(Collectors.toList());
    }

    public List<Item> getItemsPriceGreaterThan50AndLessThan20() {
        return items.stream()
                .filter(item -> item.getPrice() > 50 && item.getPrice() < 20)
                .collect(Collectors.toList());
    }

    public void saveItems(List<Item> itemList) {
        this.items.addAll(itemList);
    }
}