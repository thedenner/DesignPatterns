package TesteSpring.service;

import TesteSpring.model.Cliente;
import org.springframework.stereotype.Service;

public interface ClientService {
    Iterable<Cliente> findAll();

    Cliente findById(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}