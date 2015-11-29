using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace LucasBroadCastService
{
    abstract class Configuration
    {

        protected const String lucasExchangeName = "Lucas_Exchange";
        protected const String exchangeType = "direct";
        protected ConnectionFactory connectionFactory = new ConnectionFactory()
        {
            HostName = "localhost"
                //HostName = "192.168.107.57"
            ,
            Port = 5672
            ,
            UserName = "admin"
            ,
            Password = "admin"
        };

    }

    class BroadCastService : Configuration
    {
        private IConnection rabbitmqConnection;
        private IModel rabbitMqChannel;

        public BroadCastService()
        {
            rabbitmqConnection = connectionFactory.CreateConnection();
            rabbitMqChannel = rabbitmqConnection.CreateModel();
            rabbitMqChannel.ExchangeDeclare(lucasExchangeName, exchangeType, true);
        }

        public void publishData(String queueName, String data, String bindingKey)
        {
            rabbitMqChannel.QueueDeclare(queueName, true, false, false, null);
            rabbitMqChannel.QueueBind(queueName, lucasExchangeName, bindingKey);
            var body = Encoding.UTF8.GetBytes(data);
            rabbitMqChannel.BasicPublish(lucasExchangeName, bindingKey, null, body);
            Console.WriteLine(" [x] Data Sent to  '{0}' having key '{1}'", queueName, bindingKey);
        }

    }


    class BroadCastServiceClient : Configuration
    {
        private IConnection rabbitmqConnection;
        private IModel rabbitMqChannel;

        public BroadCastServiceClient()
        {
            rabbitmqConnection = connectionFactory.CreateConnection();
            rabbitMqChannel = rabbitmqConnection.CreateModel();
            rabbitMqChannel.ExchangeDeclare(lucasExchangeName, exchangeType, true);
        }

        public BasicDeliverEventArgs subscribeFirstMessage(String queueName, String bindingKey, int timeout)
        {

            rabbitMqChannel.QueueBind(queueName, lucasExchangeName, bindingKey);
            var consumer = new QueueingBasicConsumer(rabbitMqChannel);
            rabbitMqChannel.BasicConsume(queueName, true, consumer);
            BasicDeliverEventArgs ea = null; ;
            bool loop = true;
            while (loop)
            {
                try
                {                   
                    loop = consumer.Queue.Dequeue(timeout, out ea);
                    if (loop)
                    {
                        break;
                    }
                }
                catch (Exception e)
                {
                    e.GetBaseException();
                }

            }
            return ea;
        }

        public List<BasicDeliverEventArgs> subscribeAllData(String queueName, String bindingKey,int timeout)
        {

            rabbitMqChannel.QueueBind(queueName, lucasExchangeName, bindingKey);
            var consumer = new QueueingBasicConsumer(rabbitMqChannel);
            rabbitMqChannel.BasicConsume(queueName, true, consumer);
            List<BasicDeliverEventArgs> listData = new List<BasicDeliverEventArgs>();
            bool loop = true;
            while (loop)
            {
                try
                {
                    BasicDeliverEventArgs ea;
                    loop = consumer.Queue.Dequeue(timeout, out ea);
                    if (loop)
                    {
                        listData.Add(ea);
                    }
                }
                catch (Exception e)
                {
                    e.GetBaseException();
                }

            }
            return listData;
        }

        public void clearQueue(String queueName)
        {
            rabbitMqChannel.QueueDelete(queueName);
        }

    }


    public class LucasBroadCastClient
    {

        public static void Main(string[] args)
        {

            const String queueName = "lucasQueue";
            const String bindingKey = "key";
            const int limit = 10;
            String data = "";
            BroadCastService broadCastService = new BroadCastService();
            BroadCastServiceClient broadCastServiceClient = new BroadCastServiceClient();

            //Test 1         
            testPubhlishAndSubscribeAllMsg(broadCastService, broadCastServiceClient, queueName, data, bindingKey, limit);

            //Test 2
            data = System.Environment.MachineName + " " + new DateTime().Date;
            testPubhlishAndSubscribeOneMsg(broadCastService, broadCastServiceClient, queueName, data, bindingKey);
            
        }

        private static void testPubhlishAndSubscribeOneMsg( BroadCastService broadCastService
            , BroadCastServiceClient broadCastServiceClient, String queueName
            , String data,String bindingKey )
        {
            Console.WriteLine("\n");
           
            /* Publishing single message on the queue */
            broadCastService.publishData(queueName, data, bindingKey);

            /* Getting the first message from the queue */
            BasicDeliverEventArgs basicDeliverEventArgs = broadCastServiceClient.subscribeFirstMessage(queueName, bindingKey, 100);
            var message1 = Encoding.UTF8.GetString(((BasicDeliverEventArgs)basicDeliverEventArgs).Body);
            var routingKey1 = ((BasicDeliverEventArgs)basicDeliverEventArgs).RoutingKey;
            Console.WriteLine(" Received Msg: '{0}' key :{1} on Queue :{2}", message1, routingKey1, queueName);

            /* removing one queue from amqp server*/
            broadCastServiceClient.clearQueue(queueName);
        }

        private static void testPubhlishAndSubscribeAllMsg(BroadCastService broadCastService
         , BroadCastServiceClient broadCastServiceClient, String queueName
         , String data, String bindingKey, int limit)
        { 
            /* Publishing mulitple message on the queue */       
            for (int i = 0; i < limit; i++)
            {
                data = System.Environment.MachineName + " " + new DateTime().Date + " " + i;
                broadCastService.publishData(queueName + i, data + "-0", bindingKey + i);
                broadCastService.publishData(queueName + i, data + "-1", bindingKey + i);
                broadCastService.publishData(queueName + i, data + "-2", bindingKey + i);
                broadCastService.publishData(queueName + i, data + "-3", bindingKey + i);
                broadCastService.publishData(queueName + i, data + "-4", bindingKey + i);
                broadCastService.publishData(queueName + i, data + "-5", bindingKey + i);
            }

           
            /* Getting the all message from the queue */       
            for (int i = 0; i < limit; i++)
            {
                Console.WriteLine("\n");
                List<BasicDeliverEventArgs> listData = broadCastServiceClient.subscribeAllData(queueName + i, bindingKey + i, 100);
                for (int j = 0; j < listData.Count; j++)
                {
                    object ea = (BasicDeliverEventArgs)listData[j];
                    var message = Encoding.UTF8.GetString(((BasicDeliverEventArgs)ea).Body);
                    var routingKey = ((BasicDeliverEventArgs)ea).RoutingKey;
                    Console.WriteLine(" Received Msg: '{0}' key :{1} on Queue :{2}", message, routingKey, queueName + i);
                }
            }


            /* removing all the queue from amqp server*/
            Console.WriteLine("\n");
            for (int i = 0; i < limit; i++)
            {
                broadCastServiceClient.clearQueue(queueName + i);
                Console.WriteLine("Deleting Queue '{0}'", queueName + i);
            }
        }
        
    }
}
