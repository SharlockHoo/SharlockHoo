package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import cn.itcast.order.service.OrderService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    @RequestMapping("/orderList")
    public List<Order> queryOrderByUserId(@RequestBody List<Long> orderIds) {
        System.out.println("开始处理订单编号");
        orderService.asyncOperate();
        // 根据id查询订单并返回
        return orderService.queryOrderByIds(orderIds);
    }

    @RequestMapping("/saveOrderList")
    public String saveOrderList(@RequestBody List<Order> orderList) {
        // 根据id查询订单并返回
        return orderService.saveOrderList(orderList);
    }

    @RequestMapping("/async")
    public String async(@RequestBody List<Long> orderIds) {
        System.out.println("开始处理订单编号");
        orderService.asyncOperate2(orderIds);
        // 根据id查询订单并返回
        return "处理成功";
    }
}
