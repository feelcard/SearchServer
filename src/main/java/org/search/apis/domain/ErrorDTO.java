package org.search.apis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ErrorDTO {

    private static ErrorDTO instance = null;

    public static ErrorDTO getInstance() {
        if (instance == null) {
            synchronized (ErrorDTO.class) {
                if (instance == null) {
                    instance = new ErrorDTO();
                }
            }
        }
        return instance;
    }

    private final String state = "FAIL";
    private String url;
    private String message;

}
