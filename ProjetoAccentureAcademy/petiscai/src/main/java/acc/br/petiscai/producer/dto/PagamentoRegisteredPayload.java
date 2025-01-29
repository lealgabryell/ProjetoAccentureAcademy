package acc.br.petiscai.producer.dto;

public record PagamentoRegisteredPayload(boolean statusConfirmation, Long idPedido, int confirmationCode) {
}