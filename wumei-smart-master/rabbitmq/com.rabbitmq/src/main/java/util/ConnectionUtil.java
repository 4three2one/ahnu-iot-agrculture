package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    /**
     * 建立与RabbitMQ的连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("175.24.203.181");
        //端口
        //设置账号信息，用户名、密码、vhost
        factory.setUsername("root");
        factory.setPassword("root");
        // 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
    public static Channel getChannel() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("175.24.203.181");
        //端口
        //设置账号信息，用户名、密码、vhost
        factory.setUsername("root");
        factory.setPassword("root");
        // 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}