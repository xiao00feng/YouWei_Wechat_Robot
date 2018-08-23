package cn.webank.mumble.mpc.giftcard.common.utils;

import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.mpc.giftcard.common.annotation.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by leaflyhuang on 2017/3/29.
 */
public class ParameterFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParameterFilter.class);

    private final static String PARAMETER_MSG = "{}参数不正确";

    public static <T> boolean validate(T object, BizErrors bizErrors) {
        if (object == null) {
            LOGGER.warn("request object is null");
            return false;
        }
        Field[] fields = FieldUtils.getAllFields(object.getClass());
        checkFields(object,fields,bizErrors);
        return bizErrors.hasErrors() ? false : true;

    }

    private static <T> void checkFields(T object, Field[] fields, BizErrors bizErrors) {
        BeanWrapper wrapper = new BeanWrapperImpl(object);
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            Class clazz = wrapper.getPropertyType(field.getName());
            Object value = wrapper.getPropertyValue(field.getName());
            for (Annotation annotation : annotations) {
                if (annotation instanceof ParameterNotNull && value == null) {
                    bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()}, PARAMETER_MSG);
                    continue;
                } else if (annotation instanceof ParameterStringLength && value != null) {
                    int minLength = ((ParameterStringLength) annotation).minLength();
                    int maxLength = ((ParameterStringLength) annotation).maxLength();
                    String fieldValue = (String) value;
                    if (fieldValue.length() < minLength || fieldValue.length() > maxLength) {
                        bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()},
                                PARAMETER_MSG);
                    }
                } else if (annotation instanceof ParameterIdType && value != null) {
                    String fieldValue = (String) value;
                    if (ID_TYPE_MAPPER.get(fieldValue) == null) {
                        bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()},
                                PARAMETER_MSG);
                    }
                } else if (annotation instanceof ParameterOccuType && value != null) {
                    String fieldValue = (String) value;
                    if (OCCUPATION_MAPPER.get(fieldValue) == null) {
                        bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()},
                                PARAMETER_MSG);
                    }
                } else if (annotation instanceof ParameterRange && value != null) {
                    int maxValue = ((ParameterRange)annotation).maxValue();
                    int minValue = ((ParameterRange)annotation).minValue();
                    if(value instanceof BigDecimal){
                        BigDecimal fieldValue = (BigDecimal) value;
                        if(fieldValue.intValue() < minValue || fieldValue.intValue() > maxValue){
                            bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()},
                                    PARAMETER_MSG);
                        }
                    }else{
                        Integer fieldValue = (Integer) value;
                        if(fieldValue < minValue || fieldValue > maxValue){
                            bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{field.getName()},
                                    PARAMETER_MSG);
                        }
                    }
                } else {
                    patternField(annotation, field.getName(), value, bizErrors);
                }

            }
        }
    }

    private static void patternField(Annotation annotation, String fieldName, Object object, BizErrors bizErrors) {
        if (object == null) {
            return;
        }
        String pattern = "";
        if (annotation instanceof ParameterMemberNo) {
            pattern = "^MEDDY[0-1]{1}[0-9]{14}";
        } else if (annotation instanceof ParameterPattern) {
            pattern = ((ParameterPattern) annotation).value();
        } else if (annotation instanceof ParameterPhone) {
            pattern = "^1[0-9]{10}";
        } else if (annotation instanceof ParameterEmail) {
            pattern = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        } else if (annotation instanceof ParameterIdNo) {
            pattern = "\\d{17}\\w|\\d{14}\\w";
        } else {
            return;
        }

        String value = (String) object;
//        if ("".equals(value)) {
//            return;
//        }
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        Matcher matcher = regex.matcher(value);
        if (!matcher.matches()) {
            bizErrors.reject(ErrorStatus.ERR_PARAMETER.getCode(), new Object[]{fieldName}, PARAMETER_MSG);
        }
    }


    /**
     * 证件类型表
     */
    public final static Map<String, String> ID_TYPE_MAPPER = new HashMap<>();

    /**
     * 职业信息表
     */
    public final static Map<String, String> OCCUPATION_MAPPER = new HashMap<>();

    static {
        ID_TYPE_MAPPER.put("01", "身份证");
        ID_TYPE_MAPPER.put("02", "军人军官证");
        ID_TYPE_MAPPER.put("03", "港澳台居民通行证");
        ID_TYPE_MAPPER.put("04", "中国护照");
        ID_TYPE_MAPPER.put("05", "暂住证");
        ID_TYPE_MAPPER.put("06", "武警警官证");
        ID_TYPE_MAPPER.put("07", "临时身份证");
        ID_TYPE_MAPPER.put("08", "户口薄");
        ID_TYPE_MAPPER.put("09", "中国居民其它证");
        ID_TYPE_MAPPER.put("10", "军人士兵证");
        ID_TYPE_MAPPER.put("11", "军人文职干部证");
        ID_TYPE_MAPPER.put("12", "军人其它证件");
        ID_TYPE_MAPPER.put("13", "武警士兵证");
        ID_TYPE_MAPPER.put("14", "武警文职干部证");
        ID_TYPE_MAPPER.put("15", "武警其它证件");
        ID_TYPE_MAPPER.put("16", "外国护照");
        ID_TYPE_MAPPER.put("17", "外国公民其它证件");
        ID_TYPE_MAPPER.put("18", "对私临时证件");
        ID_TYPE_MAPPER.put("99", "其它个人证件");

        OCCUPATION_MAPPER.put("A", "律师行、会计师行、投资顾问、房地产、旅游等中介结构");
        OCCUPATION_MAPPER.put("B", "国家机关,党群组织、国企、央企");
        OCCUPATION_MAPPER.put("C", "进出口贸易公司、离案公司");
        OCCUPATION_MAPPER.put("D", "一般工商业、服务业");
        OCCUPATION_MAPPER.put("E", "金融业");
        OCCUPATION_MAPPER.put("F", "农业渔牧业、采掘业、制造业及科技信息产业");
        OCCUPATION_MAPPER.put("G", "医疗、科教、非盈利事业");
        OCCUPATION_MAPPER.put("H", "学生");
        OCCUPATION_MAPPER.put("I", "艺术品、贵金属、稀有矿业、珠宝业");
        OCCUPATION_MAPPER.put("K", "彩票销售、娱乐服务业");
        OCCUPATION_MAPPER.put("L", "无业或其他");
    }


}

