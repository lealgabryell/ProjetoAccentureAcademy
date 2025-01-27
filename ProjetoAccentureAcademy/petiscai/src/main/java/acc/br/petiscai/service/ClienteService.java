package acc.br.petiscai.service;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public String save(ClienteDto clienteDto) {
        if (!checaIdade(clienteDto)) {
            return "Cliente menor de idade!";
        } else if (this.clienteExistsByEmail(clienteDto)) {
            return "Email ja cadastrado no sistema!";
        } else if (this.clienteExistsByCpf(clienteDto)) {
            return "CPF ja cadastrado no sistema!";
        } else {
            try {
                Cliente cliente = new Cliente(clienteDto);
                this.clienteRepository.save(cliente);
                return "Cliente salvo com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }

    private boolean clienteExistsByEmail(ClienteDto clienteDto) {
        return clienteRepository.existsByEmail(clienteDto.getEmail());
    }
    private boolean clienteExistsByCpf(ClienteDto clienteDto) {
        return clienteRepository.existsByCpf(clienteDto.getCpf());
    }
    public String deleteById(long id) {
        if (findByIdBool(id)) {

            try {
                this.clienteRepository.deleteById(id);
                return "Cliente deletado com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Cliente nao encontrado";
        }

    }

    public List<Cliente> findAll() {
        try {
            return this.clienteRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean checaIdade(ClienteDto clienteDto) {
        return clienteDto.getIdade() >= 18;
    }

    public Cliente findById(long id) {

        if (this.findByIdBool(id)) {
            try {
                return this.clienteRepository.findById(id).get();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private boolean findByIdBool(long id) {
        return this.clienteRepository.findById(id).isPresent();
    }

    public String update(ClienteDto clienteDto, long id) {
        if (this.findByIdBool(id)) {
            try {
                Cliente cliente = new Cliente(id, clienteDto.getCpf(), clienteDto.getNome(), clienteDto.getEmail(),
                        clienteDto.getTelefone(), clienteDto.getEndereco(), clienteDto.getIdade());

                if (this.checaIdade(clienteDto)) {
                    this.clienteRepository.save(cliente);
                } else {
                    return "Cliente menor de idade!";
                }

                return "Cliente " + cliente.getId() + " alterado com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Cliente nao encontrado";
        }
    }

}
