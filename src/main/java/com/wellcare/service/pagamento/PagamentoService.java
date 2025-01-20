package com.wellcare.service.pagamento;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wellcare.controller.dto.pagamento.PagamentoInfoDTO;

public interface PagamentoService {

    public PaymentIntent createPaymentIntent(PagamentoInfoDTO pagamentoDto) throws StripeException;
}