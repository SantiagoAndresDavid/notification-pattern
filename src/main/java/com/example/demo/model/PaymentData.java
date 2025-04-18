package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa los datos de un pago
 */
@Getter
@Setter
@AllArgsConstructor
public class PaymentData {
    private final String transactionId;
    private final double amount;
    private final String paymentMethod;
    private final String customerName;
     
    @Override
    public String toString() {
        return "PaymentData{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}