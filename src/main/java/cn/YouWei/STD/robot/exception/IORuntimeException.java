package cn.YouWei.STD.robot.exception;

/**
 * Description: 
 */
public class IORuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IORuntimeException(String message, Exception e) {
        super(message, e);
    }
}
