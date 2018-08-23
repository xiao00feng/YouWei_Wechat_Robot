package cn.webank.mumble.mpc.giftcard.common.utils;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.Charset;

/**
 * Created by leaflyhuang on 2017/5/10.
 */
public class CodeHashUtils {
    final static String SAULT = "mpc_RfXIOTLXGe0Z1c2d";

    public final static String countHash(String code) {
        return Hashing.sha256()
                .hashString(Hashing.sha256().hashString(code + SAULT, Charset.forName("UTF-8")).toString() + code,
                        Charset.forName("UTF-8")).toString();
    }

    public static void main(String[] args) {
        System.out.println(RandomStringUtils.randomAlphanumeric(16));
        System.out.println(countHash("123456"));
        System.out.println(countHash("12345678"));
        System.out.println(countHash("12345690"));
        System.out.println(countHash("123456"));
        System.out.println(countHash("12345678"));
        System.out.println(countHash("12345690"));
    }
}
