package core.bookie.exceptions;

import lombok.RequiredArgsConstructor;

public class TokenExpiredException extends RuntimeException {


    public final String message = "Expired JWT Token. Kindly Login again";

    public TokenExpiredException(String message) {
        message = this.message;
    }
}
