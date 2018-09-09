package cn.YouWei.STD.robot.web;

/**
 * 各种服务基础类
 * 
 * @author tgf(Nov 23, 2010)
 * 
 */
public class BaseService {
	protected ServiceResult failResult() {
		return ServiceResult.failResult();
	}

	protected ServiceResult failResult(int errorCode) {
		return ServiceResult.failResult(errorCode);
	}

	/**
	 * 返回错误信息
	 * 
	 * @param messageKey
	 *            bundle键
	 * @param args
	 *            替换参数{0}...
	 * @return
	 */
	protected ServiceResult failI18nResult(String messageKey, Object... args) {
		String message = I18nMessage.getMessage(messageKey, args);
		return ServiceResult.failResult(message, messageKey);
	}

	/**
	 * 返回错误信息
	 * 
	 * @param errorMessage
	 *            错误内容
	 * @return
	 */
	protected ServiceResult failStringResult(String errorMessage) {
		return ServiceResult.failResult(errorMessage);
	}

	/**
	 * 返回错误信息
	 * 
	 * @param errorMessage
	 *            错误内容
	 * @param errorObject
	 *            错误对象
	 * @return
	 */
	protected ServiceResult failResult(String errorMessage, Object errorobject) {
		return ServiceResult.failResult(errorMessage, errorobject);
	}

	/**
	 * 返回成功结果
	 * 
	 * @return
	 */
	protected ServiceResult successResult() {
		return ServiceResult.successResult();
	}

	/**
	 * 返回成功信息
	 * 
	 * @param messageKey
	 *            bundle键
	 * @param args
	 *            替换{0}参数
	 * @return
	 */
	protected ServiceResult successI18nResult(String messageKey, Object... args) {
		String message = I18nMessage.getMessage(messageKey, args);
		return ServiceResult.successResult(message);
	}

	/**
	 * 返回成功结果
	 * 
	 * @param object
	 * @return
	 */
	protected ServiceResult successObjectResult(Object object) {
		return ServiceResult.successResult(object);
	}

	/**
	 * 结果对象是字符串的结果
	 * 
	 * @param strResult
	 * @return
	 */
	protected ServiceResult successStringResult(String strResult) {
		return ServiceResult.successResult(strResult, "");
	}

	/**
	 * 返回成功结果再加上成功信息
	 * 
	 * @param object
	 * @param messageKey
	 *            i18n bundle key
	 * @param args
	 *            替换参数{0}
	 * @return
	 */
	protected ServiceResult successI18nObjectResult(Object object,
			String messageKey, Object... args) {
		String message = I18nMessage.getMessage(messageKey, args);
		return ServiceResult.successResult(object, message);
	}
}
