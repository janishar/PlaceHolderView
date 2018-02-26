package com.mindorks.placeholderview.processor;

import java.io.IOException;

/**
 * Created by janisharali on 26/02/18.
 */
public class InvalidUseException extends IOException {
    public InvalidUseException(String message) {
        super(message);
    }
}
