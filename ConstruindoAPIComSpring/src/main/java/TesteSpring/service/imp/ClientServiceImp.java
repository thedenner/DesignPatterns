package TesteSpring.service.imp;

import TesteSpring.model.Cliente;
import TesteSpring.model.ClienteRepository;
import TesteSpring.model.Endereco;
import TesteSpring.model.EnderecoRepository;
import TesteSpring.service.ClientService;
import TesteSpring.service.ViuCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViuCepService viuCepService;

    @Override
    public Iterable <Cliente> findAll(){
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente){
      salvarClienteComCep(cliente);
    }


    @Override
    public void atualizar(Long id, Cliente cliente){
        Optional <Cliente> clienteAtualizado = clienteRepository.findById(id);
        if(clienteAtualizado.isPresent()){
            salvarClienteComCep(cliente);
        }
    }


    @Override
    public void deletar(Long id){
        clienteRepository.deleteById(id);

    }

    public void salvarClienteComCep(Cliente cliente){
        String cep = (cliente.getEndereco().getCep());
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() ->{
            Endereco novoEndereco = viuCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        } );

        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
