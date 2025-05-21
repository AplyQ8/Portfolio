using RabbitMQ.Client.Events;
using RabbitMQ.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using ClientBackLogic.Requests;
using System.Collections.Concurrent;

namespace ClientBackLogic
{
    public class RabbitMq
    {
        private static RabbitMq _instance;

        public static Guid Guid { get; private set; }
        private string _globalExchanger = "Client";
        private ConnectionFactory _factory;
        private IConnection _connection;
        private  static IModel _channel;

        private bool _alreadyConsumes;

        public EventingBasicConsumer Consumer { get; set; }

        private string _consumerTag;

        private BlockingCollection<string> _messageQueue;


        public static RabbitMq Instance()
        {
            if (_instance is null)
                _instance = new RabbitMq();
            return _instance;
        }

        public RabbitMq()
        {
            Guid = Guid.NewGuid();
            ConnectToMq();
            DeclareQueue();
            Bind();
            _alreadyConsumes = false;
            Consumer = new EventingBasicConsumer(_channel);
            _messageQueue = new BlockingCollection<string>();
            StartConsuming();
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
            //_channel.Close();
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
        public void StartConsuming()
        {
            if (_alreadyConsumes)
                return;
            _alreadyConsumes = true;
            _consumerTag = _channel.BasicConsume(queue: Guid.ToString(),
            autoAck: true,
            consumer: Consumer);
        }

        public void StopConsuming()
        {
            if (!_alreadyConsumes)
                return;

            _channel.BasicCancel(_consumerTag);
            _alreadyConsumes = false;
        }

        
       
    }
}
