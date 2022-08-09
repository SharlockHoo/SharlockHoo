package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        String url = "http://userservice/user/" + order.getUserId();

        User user = restTemplate.getForObject(url, User.class);

        order.setUser(user);

        // 4.返回
        return order;
    }

    public List<Order> queryOrderByIds(List<Long> orderIds) {
        List<Order> orderList = new ArrayList<>();
        for (Long orderId : orderIds) {
            // 1.查询订单
            Order order = orderMapper.findById(orderId);

            String url = "http://userservice/user/" + order.getUserId();

            User user = restTemplate.getForObject(url, User.class);

            order.setUser(user);

            orderList.add(order);
        }
        // 4.返回
        return orderList;
    }

    @Async
    public void asyncOperate() {
        System.out.println("开始执行异步操作！！！！");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步操作执行结束！！！！");
    }

    @Async
    public void asyncOperate2(List<Long> orderIds) {
        System.out.println("开始执行异步操作！！！！");
        List<Order> orderList = new ArrayList<>();
        for (Long orderId : orderIds) {
            // 1.查询订单
            Order order = orderMapper.findById(orderId);
            String url = "http://userservice/user/" + order.getUserId();
            User user = restTemplate.getForObject(url, User.class);
            order.setUser(user);
            orderList.add(order);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(orderList);
        System.out.println("异步操作执行结束！！！！");
    }

    public String saveOrderList(List<Order> orderList) {
        System.out.println("orderList:" + JSONObject.toJSONString(orderList));
        if (orderMapper.saveOrderList(orderList) < 0) {
            return "保存失败！";
        }
        return "保存成功！";
    }
}
