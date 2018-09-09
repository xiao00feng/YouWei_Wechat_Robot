package cn.YouWei.STD.robot.web;

import java.io.Serializable;


/**
 * result from service layer
 * every service layer method should return Result object,
 * from result object, Controller(action) layer can take diffent action,
 * like forwark to diffent template...
 * @author tgf(Mar 13, 2010)
 *
 */
public class ServiceResult implements Serializable{
	private static final long serialVersionUID = -5361570426791697898L;
	
	/** 失败信息头*/
	private static final String FAIL_SUFFIX = "[fail]";
	
	/** message from service layer method, usually is success message
	 *  because the unsuccessful messages is wrap in VerifyResult
	 */
	private String message = "";
	
	/** the result of the service, maybe it's a module, a list, and so on*/
	private Object object = null;
	
	/** whether the service method is successful finished*/
	private boolean success = false;
	
	
	/** 错误码*/
	private int errorCode = 0;
	
	/** 错误码*/
	private String errori18Code = "";
	
	/**
	 * here we will get a success result
	 * @param obj
	 * @param message
	 * @return
	 */
	public static ServiceResult successResult(Object object, String message){
		ServiceResult result = new ServiceResult();
		result.setObject(object);
		result.setSuccess(true);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 只返回对象
	 * @param obj
	 * @return
	 */
	public static ServiceResult successResult(Object obj){
		return successResult(obj, null);
	}
	
	/**
	 * 只返回成功信息
	 * @param message
	 * @return
	 */
	public static ServiceResult successResult(String message){
		return successResult(null, message);
	}
	
	/**
	 * 只标志成功,什么都不返回
	 * @return
	 */
	public static ServiceResult successResult(){		
		return successResult(null,"success!!!");
	}
	
	/**
	 * 只返回错误信息
	 * @param message
	 * @return
	 */
	public static ServiceResult failResult(String message){
		ServiceResult result = new ServiceResult();
		result.setMessage(message);		
		result.setSuccess(false);
		return result;
	}
	
	/**
	 * 只返回错误信息
	 * @param message
	 * @return
	 */
	public static ServiceResult failResult(String message, String errori18Code){
		ServiceResult result = new ServiceResult();
		result.setMessage(message);
		result.setErrori18Code(errori18Code);
		result.setSuccess(false);
		return result;
	}
	
	/**
	 * 返回错误信息及错误对象
	 * @param message
	 * @param errorObject
	 * @return
	 */
	public static ServiceResult failResult(String message, Object errorObject){
		ServiceResult result = new ServiceResult();
		result.setObject(errorObject);
		result.setMessage(message);		
		result.setSuccess(false);
		return result;
	}
	
	public static ServiceResult failResult(){
		ServiceResult result = new ServiceResult();
		result.setSuccess(false);
		result.setMessage("");
		return result;
	}
	
	public static ServiceResult failResult(int errorCode){
		ServiceResult result = new ServiceResult();
		result.setSuccess(false);
		result.setErrorCode(errorCode);
		result.setMessage("");
		return result;
	}
	
	/**
	 * 最終返回客户端的message, 如果失败会加上标记头'[fail]'
	 * @return
	 */
	public String failMessage(){
		return FAIL_SUFFIX + message;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrori18Code() {
		return errori18Code;
	}

	public void setErrori18Code(String errori18Code) {
		this.errori18Code = errori18Code;
	}

	@Override
	public String toString() {
		return "ServiceResult [object=" + object + ", success="
				+success+", message="+message+ "]";
	}	
	
}
