package service;

import model.ItemDetails;
import java.util.Optional;

public interface ItemDetailsService {
	Optional<ItemDetails> getDetailsByItemId(Long itemId);
	Boolean createDetails(ItemDetails details);
	Boolean updateDetails(ItemDetails details);
	Boolean deleteDetailsByItemId(Long itemId);
	Boolean hasDetails(Long itemId);
}