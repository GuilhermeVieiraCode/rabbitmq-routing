import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Consumidor {
    private final static String NOME_FILA = "hello";

    private static void doWork(String mensagem) throws InterruptedException{
        for(char ch: mensagem.toCharArray()){
            if(ch == '.') Thread.sleep(1000);
        }
    }
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("mqadmin");
        factory.setPassword("Admin123XX_");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean duravel = true;
        channel.queueDeclare(NOME_FILA, duravel, false, false, null);
        System.out.println("Aguardando mensagens...");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Recebeu: " + mensagem);
            try{
                doWork(mensagem);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("[x] Feito");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(NOME_FILA, autoAck, deliverCallback, consumerTag->{ });
    }
}


