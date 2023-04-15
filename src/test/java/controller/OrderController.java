package controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/processOrder")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(
        @Valid @RequestBody OrderRequest orderReq,
        UriComponentsBuilder uriComponentsBuilder
    ) {
        int order = service.createNewOrder(orderReq);
        UriComponents uriComponents = uriComponentsBuilder.path("/order").buildAndExpand(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.getAllOrder();
    }
}
