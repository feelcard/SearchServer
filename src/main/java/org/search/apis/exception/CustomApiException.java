package org.search.apis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomApiException extends RuntimeException {

    private String url;

    private String message;

}
