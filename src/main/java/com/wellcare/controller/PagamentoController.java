package com.wellcare.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wellcare.controller.dto.pagamento.PagamentoInfoDTO;
import com.wellcare.service.pagamento.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "public/pagamento")
public class PagamentoController {

    @Autowired
    public PagamentoService pagamentoService;

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PagamentoInfoDTO paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = pagamentoService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
