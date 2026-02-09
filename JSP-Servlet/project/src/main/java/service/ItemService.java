package service;

import model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {
	List<Item> getItemsWithDetails();
	Optional<Item> getItemWithDetails(Long id);
	Optional<Item> getItemByName(String name);
	Boolean createItem(Item item);
	Boolean updateItem(Item item);
	Boolean removeItem(Long id);
}