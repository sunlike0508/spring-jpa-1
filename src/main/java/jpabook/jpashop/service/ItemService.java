package jpabook.jpashop.service;


import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    // 변경 감지
    @Transactional
    public Item updateItem(Long itemId, Book bookForm) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(bookForm.getPrice());
        findItem.setName(bookForm.getName());
        findItem.setStockQuantity(bookForm.getStockQuantity());

        return findItem;
    }


    public List<Item> findItems() {
        return itemRepository.findAll();
    }


    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
