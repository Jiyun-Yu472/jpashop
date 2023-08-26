package jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;

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
	public String create(BookForm form) {
		Book book = new Book();
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		
		itemService.saveItem(book);
		
		return "redirect:/items";
	}
	
	@GetMapping("/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		
		return "items/itemList";
	}
	
	@GetMapping("items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book item = (Book) itemService.findOne(itemId);
		
		BookForm form = new BookForm();
		form.setId(item.getId());
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());
		
		model.addAttribute("form", form);
		
		return "items/updateItemForm";
		
	}
	
	@PostMapping("items/{itemId}/edit")
	public String updateItem(@ModelAttribute BookForm form) {
		Book book = new Book();
		
		// JAP를 통해서 식별자를 구분할 수 있는 객체를 준영속 객체라 한다.(영속 객체가 아니기 때문에 dirty checking이 발생하지 않는다.)
		// 준영속 객체를 변경하는 방법은 2가지가 있다.
		// 1.변경감지: ItemService의 updateItem함수처럼 영속객체를 불러와서 더티체킹(변경감지)를 하는 방법.
		// 2.병함: ItemRepository의 save함수의 em.merge()를 사용하는 방법.(준영속 객체를 영속 상태로 변경해서 더티체킹하는 것. 변경감지와 원리는 동일하고 em.merge()에서 반환된 값은 영속 객체를 반환한다.)
		// 2-1 병합을 사용하면 해당 엔티티의 모든 값을 병경하기때문에, 병합시 셋팅된 값이 없으면 null로 치환하여 값을 변경한다.
		// 결론: 변경감지를 사용하자.
		
		book.setId(form.getId());
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		
		itemService.saveItem(book);
		
		return "redirect:/items";
	}
}
