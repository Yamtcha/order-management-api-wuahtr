package com.order.management.api.yamkelavenfolo.model.errorHandling;

public record ErrorDetail(String orderNumber, String errorType, String errorMessage, String errorCode) {
}
