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
        }
        
        if (clienteRepository.existsByEmail(clienteDto.getEmail())) {
            return "Email já cadastrado no sistema!";
        }

        if (clienteRepository.existsByCpf(clienteDto.getCpf())) {
            return "CPF já cadastrado no sistema!";
        }

        try {
            Cliente cliente = new Cliente(
                    clienteDto.getCpf(),
                    clienteDto.getNome(),
                    clienteDto.getEmail(),
                    clienteDto.getTelefone(),
                    clienteDto.getEndereco(),
                    clienteDto.getIdade());
            this.clienteRepository.save(cliente);
            return "Cliente salvo com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // public String save(ClienteDto clienteDto) {
    //     if (checaIdade(clienteDto)) {
    //         try {
    //             Cliente cliente = new Cliente(clienteDto.getCpf(), clienteDto.getNome(), clienteDto.getEmail(),
    //                     clienteDto.getTelefone(), clienteDto.getEndereco(), clienteDto.getIdade());
    //             this.clienteRepository.save(cliente);
    //             return "Cliente salvo com sucesso!";
    //         } catch (Exception e) {
    //             return e.getMessage();
    //         }
    //     } else {
    //         return ("Cliente menor de idade!");
    //     }
    // }

    public String deleteById(long id) {
        if (findByIdBool(id)) {

            try {
                this.clienteRepository.deleteById(id);
                return "Cliente deletado com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Cliente não encontrado";
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

        if (findByIdBool(id)) {
            try {
                return this.clienteRepository.findById(id).get();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean findByIdBool(long id) {
        return this.clienteRepository.findById(id).isPresent();
    }

    public String update(ClienteDto clienteDto, long id) {
        if (findByIdBool(id)) {
            try {
                Cliente cliente = new Cliente(id, clienteDto.getCpf(), clienteDto.getNome(), clienteDto.getEmail(),
                        clienteDto.getTelefone(), clienteDto.getEndereco(), clienteDto.getIdade());

                if (checaIdade(clienteDto)) {
                    this.clienteRepository.save(cliente);
                } else {
                    return "Cliente menor de idade!";
                }

                return "Cliente " + cliente.getId() + " alterado com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Cliente não encontrado";
        }
    }

}
