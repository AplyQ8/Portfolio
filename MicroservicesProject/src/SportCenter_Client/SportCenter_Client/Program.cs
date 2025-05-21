using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using SportCenter_Client;

RabbitMq rabbitmq = new RabbitMq();

rabbitmq.DeclareQueue();
rabbitmq.Bind();
rabbitmq.StartListeningForQueue();
var consoleApp = new ConsoleApplication();

consoleApp.StartApplication();


rabbitmq.Unbind();

