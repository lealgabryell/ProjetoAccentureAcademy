package acc.br.petiscai.service;

import acc.br.petiscai.entity.Pagamento;
import acc.br.petiscai.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void save(Pagamento pagamento) {
        try{
            pagamentoRepository.save(pagamento);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
