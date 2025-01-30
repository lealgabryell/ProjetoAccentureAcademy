package acc.br.petiscai.controller;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteControllerTeste {

    @Autowired
    ClienteController clienteController;
    @MockitoBean
    ClienteRepository clienteRepository;


    // @BeforeEach
    // void setup() {
    //     List<Cliente> clientes = new ArrayList<>();
    //     List<Cliente> clientesEmpty = new ArrayList<>();

    //     Cliente cliente1 = new Cliente(1, "08877712234", "Gabryell",
    //             "gabryell@gmail.com", "83988887777", "Rua da beira, 989, Beira", 23);
    //     Cliente cliente2 = new Cliente(2, "09812312345", "Luan",
    //             "luan@gmail.com", "83988888989", "Rua da entrada, 789, Entrada", 53);
    //     ClienteDto clienteDto = new ClienteDto("08877712234", "Gabryell",
    //             "gabryell@gmail.com", "83988887777", "Rua da beira, 989, Beira", 23);

    //     clientes.add(cliente1);
    //     clientes.add(cliente2);

    //     when(clienteRepository.save(new Cliente())).thenReturn(cliente1);
    //     when(clienteRepository.findAll()).thenReturn(clientes);
    //     when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));
    // }

    // @Test
    // void testSaveReturnOK() {
    //     ClienteDto c = new ClienteDto("08877712234", "Gabryell",
    //             "gabryell@gmail.com", "83988887777", "Rua da beira, 989, Beira", 23);
    //     ResponseEntity<String> retorno = this.clienteController.save(c);

    //     assertEquals(HttpStatus.CREATED, retorno.getStatusCode());
    // }

    // @Test
    // void testSaveReturnNOTACCEPTABLE() {
    //     ClienteDto cliente = new ClienteDto("08877712234", "Gabryell",
    //             "gabryell@gmail.com", "83988887777", "Rua da beira, 989, Beira", 16);
    //     ResponseEntity<String> retorno = this.clienteController.save(cliente);

    //     assertEquals(HttpStatus.NOT_ACCEPTABLE, retorno.getStatusCode());
    // }

    // @Test
    // void testUpdateReturnBADREQUEST() {
    //     ClienteDto cliente = new ClienteDto("08877712234", "Gabryell",
    //             "gabryell@gmail.com", "83988887777", "Rua da beira, 989, Beira", 25);

    //     ResponseEntity<String> retorno = this.clienteController.update(cliente, 1);

    //     assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
    // }

    @Test
    void testFindAllReturnOK() {
        ResponseEntity<List<Cliente>> retorno = this.clienteController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @Test
    void testFindAllReturnTamanhoDaLista() {
        ResponseEntity<List<Cliente>> retorno = this.clienteController.findAll();
        assertEquals(2, retorno.getBody().size());
    }

    @Test
    void testFindByIdReturnOK() {
        ResponseEntity<Cliente> retorno = this.clienteController.findById(1L);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @Test
    void testFindByIdReturnNOTFOUND() {
        ResponseEntity<Cliente> retorno = this.clienteController.findById(-1L);
        assertEquals(HttpStatus.NOT_FOUND, retorno.getStatusCode());
    }

}
