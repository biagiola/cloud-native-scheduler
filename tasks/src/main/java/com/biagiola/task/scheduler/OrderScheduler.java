package com.biagiola.task.scheduler;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderScheduler {
//    @Autowired
//    OrderRepository orderRepository;

    private int counter = 0;
    @Scheduled(fixedRate = 1000)
    public void processPendingOrders() {
//        System.out.println("Processing Orders no dynamic: " + counter++);

//        List<Order> orders = orderRepository.findByStatus("PENDING");
//        orders.forEach(order -> {
//            order.setStatus("COMPLETED");
//            System.out.println(order.getCustomerEmail());
//            orderRepository.save(order);
//        });
//        System.out.println("Processed pending orders"+ orders.size());
    }
}
