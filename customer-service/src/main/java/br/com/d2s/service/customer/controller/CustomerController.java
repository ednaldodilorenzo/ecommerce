package br.com.d2s.service.customer.controller;

import br.com.d2s.service.customer.dto.PostUserDto;
import br.com.d2s.service.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public void postUser(@RequestBody @Valid PostUserDto dto) {
        customerService.save(dto);
    }
}
