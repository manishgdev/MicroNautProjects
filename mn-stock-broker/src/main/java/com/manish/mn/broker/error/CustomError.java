package com.manish.mn.broker.error;

import com.manish.mn.api.IRestApiResponse;

public record CustomError(int code, String error, String message) implements IRestApiResponse {
}
