package br.com.d2s.service.customer.service;

import br.com.d2s.service.customer.dto.PostUserDto;
import br.com.d2s.service.customer.model.User;

public interface CustomerService {

    void save(PostUserDto dto);
}
