package com.reuse.predefined.exceptions.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private String message;
}
