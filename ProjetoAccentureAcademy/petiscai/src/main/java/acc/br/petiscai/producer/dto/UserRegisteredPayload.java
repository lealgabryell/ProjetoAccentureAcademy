package acc.br.petiscai.producer.dto;

public record UserRegisteredPayload(String fullName, String emailAddress, int confirmationCode) {
}