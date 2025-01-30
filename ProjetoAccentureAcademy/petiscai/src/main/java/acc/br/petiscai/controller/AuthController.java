package acc.br.petiscai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.service.ClienteService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("clienteDto")) {
            model.addAttribute("clienteDto", new ClienteDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute ClienteDto clienteDto, Model model) {
        ResponseEntity<String> response = clienteService.save(clienteDto);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            model.addAttribute("success", "Cadastro realizado com sucesso!");
            return "login";
        } else {
            model.addAttribute("clienteDto", clienteDto);
            model.addAttribute("error", response.getBody());
            return "register";
        }
    }
}

