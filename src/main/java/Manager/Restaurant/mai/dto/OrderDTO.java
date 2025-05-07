package Manager.Restaurant.mai.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private Long userId;
    private Long addressId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long voucherId;
}
