package cn.webank.mumble.mpc.giftcard.common.utils;

import java.util.Calendar;
import java.util.Date;

import cn.webank.mumble.mpc.giftcard.common.dto.CardIdInfoDTO;

/**
 * 计算有效期
 * Created by nickkli on 2017/7/20.
 */
public class DateUtils {

    /**
     * 增加天数
     * @param now
     * @param days
     * @return
     */
    public final static Date addDays(Date now, int days){
        Calendar cd = Calendar.getInstance();
        cd.setTime(now);
        cd.add(Calendar.DATE, days);
        return cd.getTime();
    }

    /**
     * 增加月份
     * @param now
     * @param months
     * @return
     */
    public final static Date addMonths(Date now, int months){
        Calendar cd = Calendar.getInstance();
        cd.setTime(now);
        cd.add(Calendar.MONTH, months);
        return cd.getTime();
    }

    /**
     * 增加年份
     * @param now
     * @param years
     * @return
     */
    public final static Date addYears(Date now, int years){
        Calendar cd = Calendar.getInstance();
        cd.setTime(now);
        cd.add(Calendar.YEAR, years);
        return cd.getTime();
    }

    /**
     * 计算有效期起始时间
     * @param cardInfoDTO
     * @return
     */
    public final static Date calValidStart(CardIdInfoDTO cardInfoDTO){
        if(null == cardInfoDTO){
            return null;
        }
        Date validStart = null;
        if(cardInfoDTO.getValidType() == 1){
            validStart = addDays(new Date(), cardInfoDTO.getValidBegin());
            return validStart;
        }else{
            return cardInfoDTO.getValidStart();
        }
    }

    /**
     * 计算有效期结束时间
     * @param cardInfoDTO
     * @return
     */
    public final static Date calValidEnd(CardIdInfoDTO cardInfoDTO){
        if(null == cardInfoDTO){
            return null;
        }
        Date validEnd = null;
        if(cardInfoDTO.getValidType() == 1){
            Date validStart = addDays(new Date(), cardInfoDTO.getValidBegin());
            validEnd = addDays(validStart, cardInfoDTO.getValid());
            return validEnd;
        }else{
            return cardInfoDTO.getValidEnd();
        }
    }
}
