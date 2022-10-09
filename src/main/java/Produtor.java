import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {
    private final static String NOME_FILA = "hello";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("mqadmin");
        factory.setPassword("Admin123XX_");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            boolean duravel = true;
            channel.queueDeclare(NOME_FILA, duravel, false, false, null);
            String mensagem = String.join(" ", args);
            channel.basicPublish("",
                                NOME_FILA,
                                MessageProperties.PERSISTENT_TEXT_PLAIN,
                                mensagem.getBytes("UTF-8"));
            System.out.println("[x] Enviou: " + mensagem);
        }
    }
}


