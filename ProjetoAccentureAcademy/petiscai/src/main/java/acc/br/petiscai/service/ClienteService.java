package acc.br.petiscai.service;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public String save(ClienteDto clienteDto) {
        try {
            Cliente cliente = new Cliente(clienteDto.getCpf(), clienteDto.getNome(), clienteDto.getEmail(),
                    clienteDto.getTelefone(), clienteDto.getEndereco(), clienteDto.getIdade());
            this.clienteRepository.save(cliente);
            return "Cliente salvo com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String deleteById(long id) {
        try {
            this.clienteRepository.deleteById(id);
            return "Cliente deletado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Cliente> findAll() {
        try {
            return this.clienteRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checaIdade(ClienteDto clienteDto) {
        return clienteDto.getIdade() >= 18;
    }

    public Cliente findById(long id) {
        return this.clienteRepository.findById(id).get();
    }
    public boolean findByIdBool(long id) {
        return this.clienteRepository.findById(id).isPresent();
    }

    public String update(ClienteDto clienteDto, long id) {
        try {
            Cliente cliente = new Cliente(id, clienteDto.getCpf(), clienteDto.getNome(), clienteDto.getEmail(),
                    clienteDto.getTelefone(), clienteDto.getEndereco(), clienteDto.getIdade());
            this.clienteRepository.save(cliente);
            return "Cliente "+ cliente.getId() + " alterado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
