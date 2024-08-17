package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;


    @Test
    @DisplayName("상품 주문 성공")
    void order() {
        Member member = createMember();

        Book book = Book.createBook("JPA 책", 10000, 10);

        em.persist(book);

        int orderCount = 2;
        long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        em.flush();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getTotalPrice()).isEqualTo(20000);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원");
        member.setAddress(new Address("서울", "경리단길", "123-123"));
        em.persist(member);
        return member;
    }


    @Test
    @DisplayName("주문 취소 성공")
    void cancel() {

        Member member = createMember();

        Book book = Book.createBook("JPA 취소", 10000, 10);

        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancel(orderId);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }


    @Test
    @DisplayName("상품주문 재고수량초괴")
    void test1() {

        Member member = createMember();

        Book book = Book.createBook("JPA 책", 1000, 10);

        em.persist(book);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
    }
}