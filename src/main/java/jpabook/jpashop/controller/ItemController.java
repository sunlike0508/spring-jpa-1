package jpabook.jpashop.controller;


import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/items/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }


    @PostMapping("/items/new")
    public String create(BookForm bookForm) {

        Book book = Book.createBook(bookForm);

        itemService.saveItem(book);

        return "redirect:/";
    }


    @GetMapping("/items")
    public String list(Model model) {

        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);

        return "items/itemList";
    }


    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findItem(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";
    }


    @PostMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm bookForm) {

        Book book = Book.updateBook(bookForm);

        // 병합
        // itemService.saveItem(book);

        // 변경감지
        itemService.updateItem(itemId, book);

        return "redirect:/items";
    }
}
