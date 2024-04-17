package mn.btv.api.exception;

import lombok.Data;

@Data
public class CustomError {
    private int status;
    private String message;
}
