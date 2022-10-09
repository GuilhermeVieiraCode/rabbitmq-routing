import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
    private final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("mqadmin");
        factory.setPassword("Admin123XX_");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            String text = "Guilherme Alves Vieira";

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");


            channel.basicPublish(EXCHANGE_NAME,
                                "string",
                    null,
                                text.getBytes("UTF-8"));

            System.out.println("[x] Enviou: " + text + ": " + "string");
        }
    }
}


