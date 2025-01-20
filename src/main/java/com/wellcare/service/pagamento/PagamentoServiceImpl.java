package com.wellcare.service.pagamento;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.wellcare.controller.dto.pagamento.PagamentoInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Override
    public PaymentIntent createPaymentIntent(PagamentoInfoDTO pagamentoDto) throws StripeException {
        Stripe.apiKey = secretKey;

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(pagamentoDto.getAmount())
                        .setCurrency(pagamentoDto.getCurrency())
                        .addAllPaymentMethodType(paymentMethodTypes)
                        .build();

        return PaymentIntent.create(params);
    }
}
