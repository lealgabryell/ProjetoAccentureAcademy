package acc.br.petiscai.service;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.repository.ClienteRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;    

    @Transactional
    public ResponseEntity<String> save(ClienteDto clienteDto) {
        if (!checaIdade(clienteDto)) {
            return new ResponseEntity<>("Cliente menor de idade!", HttpStatus.NOT_ACCEPTABLE);
        }

        if (clienteExistsByCpf(clienteDto)) {
            return new ResponseEntity<>("CPF já cadastrado no sistema!", HttpStatus.CONFLICT);
        }

        if (clienteExistsByEmail(clienteDto)) {
            return new ResponseEntity<>("Email já cadastrado", HttpStatus.CONFLICT);
        }

        try {
            Cliente cliente = new Cliente(clienteDto);
            this.clienteRepository.save(cliente);
            cliente.setPassword(passwordEncoder.encode(clienteDto.getPassword()));
            clienteRepository.save(cliente);
            return new ResponseEntity<>("Cliente salvo com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    
    }

    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
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

    @Transactional
    public ResponseEntity<String> update(ClienteDto clienteDto, Long id) {
        if (!findByIdBool(id)) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            if (!checaIdade(clienteDto)) {
                return new ResponseEntity<>("Cliente menor de idade!", HttpStatus.NOT_ACCEPTABLE);
            }

            Cliente cliente = clienteRepository.findById(id).get();
            cliente.setCpf(clienteDto.getCpf());
            cliente.setNome(clienteDto.getNome());
            cliente.setEmail(clienteDto.getEmail());
            cliente.setTelefone(clienteDto.getTelefone());
            cliente.setEndereco(clienteDto.getEndereco());
            cliente.setIdade(clienteDto.getIdade());
            cliente.setPassword(passwordEncoder.encode(clienteDto.getPassword()));

            clienteRepository.save(cliente);
            return new ResponseEntity<>("Cliente alterado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
