package com.moviebooking.microservices.adapter;

import com.moviebooking.microservices.model.PaymentGatewayResponse;
import com.moviebooking.microservices.util.Constants;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceAdapter {

    public PaymentGatewayResponse initiatePayment() {
        // TODO: invoke RESTApis for initiating payment gateway via UPI/NET/CARD payment methods
        PaymentGatewayResponse paymentGatewayResponse = new PaymentGatewayResponse();
        paymentGatewayResponse.setPaymentStatus(Constants.PAYMENT_STATUS_COMPLETED);
        return paymentGatewayResponse;
    }
}
