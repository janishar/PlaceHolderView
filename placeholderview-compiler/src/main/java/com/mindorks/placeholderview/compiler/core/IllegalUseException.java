package com.mindorks.placeholderview.compiler.core;

import java.io.IOException;

/**
 * Created by janisharali on 26/02/18.
 */
public class IllegalUseException extends IOException {
    public IllegalUseException(String message) {
        super(message);
    }
}
