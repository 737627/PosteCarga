package postecarga.redis;

import redis.clients.jedis.JedisPubSub;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**

 */
public class RedisMessageListener extends JedisPubSub {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Received Redis message on channel " + channel + ": " + message);

        try {
            JsonNode jsonMessage = objectMapper.readTree(message);
            switch (channel) {
                case "PosteCargaEstadoCambio":
                    handlePosteCargaEstadoCambio(jsonMessage);
                    break;
                case "CargaIniciada":
                    handleCargaIniciada(jsonMessage);
                    break;
                case "CargaFinalizada":
                    handleCargaFinalizada(jsonMessage);
                    break;
                case "ValidacionCuenta":
                    handleValidacionCuenta(jsonMessage);
                    break;
                default:
                    System.out.println("Unknown channel or no action defined for: " + channel);
                    break;
            }
        } catch (IOException e) {
            System.err.println("Failed to parse message: " + e.getMessage());
        }
    }

    private void handlePosteCargaEstadoCambio(JsonNode message) {
        String idPoste = message.get("idPoste").asText();
        String nuevoEstado = message.get("nuevoEstado").asText();
        long timestamp = message.get("timestamp").asLong();

        System.out.println("Poste de carga ID: " + idPoste + " cambió a estado: " + nuevoEstado + " en el tiempo: " + timestamp);
        // Logic to update database or send event to web service
    }

    private void handleCargaIniciada(JsonNode message) {
        String idCarga = message.get("idCarga").asText();
        String idPoste = message.get("idPoste").asText();
        String matriculaVehiculo = message.get("matriculaVehiculo").asText();
        long msInicio = message.get("msInicio").asLong();

        System.out.println("Carga iniciada. ID de carga: " + idCarga + ", ID de poste: " + idPoste + ", Matrícula: " + matriculaVehiculo + ", Tiempo de inicio: " + msInicio);
        // Logica?¿
    }

    private void handleCargaFinalizada(JsonNode message) {
        String idCarga = message.get("idCarga").asText();
        double kWhCargados = message.get("kWhCargados").asDouble();
        double importeTotal = message.get("importeTotal").asDouble();
        String estadoFinal = message.get("estadoFinal").asText();

        System.out.println("Carga finalizada. ID de carga: " + idCarga + ", kWh cargados: " + kWhCargados + ", Importe total: " + importeTotal + ", Estado final: " + estadoFinal);
        // Logica?¿
    }

    private void handleValidacionCuenta(JsonNode message) {
        boolean validacion = message.get("validacion").asBoolean();

        System.out.println("Resultado de validación de cuenta: " + (validacion ? "Aprobada" : "Rechazada"));
        // Logica?¿
    }
}
