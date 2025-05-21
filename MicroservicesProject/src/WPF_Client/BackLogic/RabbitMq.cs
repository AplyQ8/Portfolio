using BackLogic.Requests;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Threading.Channels;

namespace BackLogic
{
    public class RabbitMq
    {
        private static RabbitMq _instance;

        public static Guid Guid { get; private set; }
        private string _globalExchanger = "Client";
        private ConnectionFactory _factory;
        private IConnection _connection;
        private static IModel _channel;

        public EventingBasicConsumer Consumer { get; private set; }


        public static RabbitMq Instance()
        {
            if(_instance is null)
                _instance = new RabbitMq();
            return _instance;
        }

        public RabbitMq()
        {
            Guid = Guid.NewGuid();
            ConnectToMq();
            DeclareQueue();
            Bind();
            
            Consumer = new EventingBasicConsumer(_channel);
        }

        private void ConnectToMq()
        {
            _factory = new ConnectionFactory
            {
                HostName = "localhost",
                UserName = "admin",
                Password = "admin",
                //VirtualHost ="/",
                Port = 5672
            };
            _connection = _factory.CreateConnection();
            _channel = _connection.CreateModel();
        }

        public void DeclareQueue()
        {
            _channel.QueueDeclare(
                queue: Guid.ToString(),
                durable: true,
                exclusive: false,
                autoDelete: true,
                arguments: null);
        }
        public void Bind()
        {
            _channel.QueueBind(
                exchange: "Client",
                queue: Guid.ToString(),
                routingKey: Guid.ToString());
        }
        public void Unbind()
        {
            _channel.QueueUnbind(
                exchange: "Client",
                queue: Guid.ToString(),
                routingKey: Guid.ToString()
                );
        }

        public static void PublicMessage(string exchangeNode, string routingKey, BaseRequest request)
        {
            request.Guid = Guid.ToString();
            var message = JsonConvert.SerializeObject(request);
            var body = Encoding.UTF8.GetBytes(message);
            _channel.BasicPublish(
                exchange: exchangeNode,
                routingKey: routingKey,
                basicProperties: null,
                body: body);
        }
    }
}