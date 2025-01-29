package acc.br.petiscai.consumer.dtos;

public record PagamentoConfirmationPayload(boolean statusConfirmation, Long idPedido, int confirmationCode) {
}
