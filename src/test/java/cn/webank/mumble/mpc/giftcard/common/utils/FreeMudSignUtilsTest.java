package cn.webank.mumble.mpc.giftcard.common.utils;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leaflyhuang on 2017/4/20.
 */
public class FreeMudSignUtilsTest extends JunitBaseTest {

    @Value("${freemud.sign.key}")
    private String signKey;

    @Test
    public void testCountSign() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("order_id", "poNV6xGPUDSD30v5zY3_GE2F3QDs");
        request.put("action", "code.verify");
        request.put("code", "180384185747113");
        request.put("redeem_time", "2016-03-07 09:02:01");
        String sign = FreeMudSignUtils.countSign(request, signKey);
        System.out.println("{\"toUserName\":\"gh_806031d64a4b\",\"fromUserName\":\"omj0Ej3Z5CvAs8xI6otbG2BUuLu4\",\"createTime\":\"1510845317\",\"messageType\":\"event\",\"event\":\"user_get_card\",\"cardId\":\"pmj0Ej4bvau5JRhmZsaXDUMU_fp0\",\"userCardCode\":\"100863933357\",\"friendUserName\":\"\",\"authorizerAppId\":\"wx2df7a6625d59b75d\",\"openId\":\"omj0Ej3Z5CvAs8xI6otbG2BUuLu4\",\"isGiveByFriend\":\"0\",\"oldUserCardCode\":\"\",\"isRestoreMemberCard\":\"0\"}");
        Assert.assertEquals(sign, "16ed8cdbf91d2c934420ce4a6874a854");
    }

    public static void main(String[] args) {
//        java.util.regex.Pattern regex = java.util.regex.Pattern.compile("[0-9a-zA-Z]{32}");
        System.out.println(new String(Base64.decodeBase64("CQkJPFJFUVVFU1RfRlBLSlhYIGNsYXNzPSJSRVFVRVNUX0ZQS0pYWCI+CgkJCQk8RlBLSlhYX0ZQVFhYIGNsYXNzPSJGUEtKWFhfRlBUWFgiPgoJCQkJCTwhLS0g5Y+R56Wo6K+35rGC5ZSv5LiA5rWB5rC05Y+3IOW/hemhuyAtLT4KCQk\n" +
                "JCQk8RlBRUUxTSD5NWjAwMTE3MDkxNTAwMDAwNDAwMTwvRlBRUUxTSD4KCQkJCQk8IS0tIOaVsOaNruadpea6kCDlv4XpobsgMS3nmb7og5znlLXlrZDlj5HnpajlubPlj7AgMi3nvZHnq5kgMy3lip7lhazlrqQgNC3lhbbku5bnrKzkuInmlrkgLS0+CgkJCQkJPFNKTFk\n" +
                "+NTwvU0pMWT4KCQkJCQk8IS0tIOmkkOWOhee8luWPtyDlv4XpobsgLS0+CgkJCQkJPFhIRl9ETT5NWjAwMTwvWEhGX0RNPgoJCQkJCTwhLS0g6ZSA5pa56K+G5Yir5Y+3ICAtLT4KCQkJCQk8WEhGX05TUlNCSD48L1hIRl9OU1JTQkg+CgkJCQkJPCEtLSDplIDmlrnlkI3\n" +
                "np7AgLS0+CgkJCQkJPFhIRl9NQz48L1hIRl9NQz4KCQkJCQk8IS0tIOmUgOaWueWcsOWdgOOAgeeUteivnSAtLT4KCQkJCQk8WEhGX0RaREg+PC9YSEZfRFpESD4KCQkJCQk8IS0tIOmUgOaWuemTtuihjOOAgui0puWPtyAtLT4KCQkJCQk8WEhGX1lIWkg+PC9YSEZfWUh\n" +
                "aSD4KCQkJCQk8IS0tIOi0rei0p+aWueWQjeensCAg5b+F6aG7LS0+CgkJCQkJPEdIRl9NQz7puqblvZPlirPvvIjkuK3lm73vvInmnInpmZDlhazlj7g8L0dIRl9NQz4KCQkJCQk8IS0tIOi0rei0p+aWueivhuWIq+WPtyAtLT4KCQkJCQk8R0hGX05TUlNCSD45MTMxMDA\n" +
                "wMDYwNzI5NTgzN0c8L0dIRl9OU1JTQkg+CgkJCQkJPCEtLSDotK3otKfmlrnlnLDlnYDnlLXor50gLS0+CgkJCQkJPEdIRl9EWkRIPuiZueaihei3rzE4MDHlj7flh6/np5Hlm73pmYXlpKfljqYxOOalvDAyMS0yNDA3MTgwMDwvR0hGX0RaREg+CgkJCQkJPCEtLSDotK3\n" +
                "otKfmlrnpk7booYzjgILotKblj7cgLS0+CgkJCQkJPEdIRl9ZSFpIPuaLm+WVhumTtuihjOaWsOWuouermeaUr+ihjDIxMTY4MTExNDYxMDAwMTwvR0hGX1lIWkg+CgkJCQkJPCEtLSDotK3otKfmlrnpgq7nrrEgLS0+CgkJCQkJPEdIRl9FTUFJTD5uYW5jeS53YW5nMDF\n" +
                "AY24ubWNkLmNvbTwvR0hGX0VNQUlMPgoJCQkJCTwhLS0g5byA56Wo5ZGYIOW/hemhuyDlrZfmrrXplb84IC0tPgoJCQkJCTxLUFk+bWNkeGN4PC9LUFk+CgkJCQkJPCEtLSDmlLbmrL7lkZgg5a2X5q616ZW/OCAtLT4KCQkJCQk8U0tZPjwvU0tZPgoJCQkJCTwhLS0g5aS\n" +
                "N5qC45Lq6IOWtl+autemVvzggLS0+CgkJCQkJPEZIUj48L0ZIUj4KCQkJCQk8IS0tIOW8gOelqOexu+WeiyDlv4XpobsgIDEt5q2j56WoIDIt57qi56WoIC0tPgoJCQkJCTxLUExYPjE8L0tQTFg+CgkJCQkJPCEtLSDljp/lj5Hnpajku6PnoIEg5a2X5q616ZW/MTIga3B\n" +
                "seOS4ujLml7blv4XpobsgLS0+CgkJCQkJPFlGUF9ETT48L1lGUF9ETT4KCQkJCQk8IS0tIOWOn+WPkeelqOWPt+eggSDlrZfmrrXplb84IGtwbHjkuLoy5pe25b+F6aG7IC0tPgoJCQkJCTxZRlBfSE0+PC9ZRlBfSE0+CgkJCQkJPCEtLSDku7fnqI7lkIjorqHph5Hpop0\n" +
                "g5b+F6aG7IOWtl+autemVvzE2IC0tPgoJCQkJCTxLUEhKSkU+NS4wMDwvS1BISkpFPgoJCQkJCTwhLS0g6K6i5Y2V6YeR6aKdIOW/hemhuyDlrZfmrrXplb8xNiAtLT4KCQkJCQk8RERKRT41LjAwPC9EREpFPgoJCQkJCTwhLS0g6K6i5Y2V5Y+3IC0tPgoJCQkJCTxEREg\n" +
                "+QVFBQXBONGlRcThWbFQ0S0JsU09zWTRjYnB1SjwvRERIPgoJCQkJCTwhLS0g6K6i5Y2V5pe26Ze0IOagvOW8j1lZWVktTU0tREQgSEg6TUk6U1MgLS0+CgkJCQkJPEREREFURT4yMDE3LTA5LTE1IDE0OjQzOjAwPC9ERERBVEU+CgkJCQkJPCEtLSDlpIfms6gg5a2X5q6\n" +
                "16ZW/MjAwIC0tPgoJCQkJCTxCWj48L0JaPgoJCQkJCTwhLS0g5aSH55So5a2X5q61MSAtLT4KCQkJCQk8QllaRDE+PC9CWVpEMT4KCQkJCQk8IS0tIOWkh+eUqOWtl+autTIgLS0+CgkJCQkJPEJZWkQyPjwvQllaRDI+CgkJCQkJPCEtLSDlpIfnlKjlrZfmrrUzIC0tPgo\n" +
                "JCQkJCTxCWVpEMz48L0JZWkQzPgoJCQkJCTwhLS0g5aSH55So5a2X5q61NCAtLT4KCQkJCQk8QllaRDQ+PC9CWVpEND4KCQkJCQk8IS0tIOWkh+eUqOWtl+autTUgLS0+CgkJCQkJPEJZWkQ1PjwvQllaRDU+CgkJCQk8L0ZQS0pYWF9GUFRYWD4KCQkJCTxGUEtKWFhfWE1\n" +
                "YWFMgY2xhc3M9IkZQS0pYWF9YTVhYOyIgc2l6ZT0iMSI+CgkJCQkJPEZQS0pYWF9YTVhYPgoJCQkJCQk8IS0tIOmhueebrue8lueggSDlv4Xpobsg5a2X5q616ZW/MjAgLS0+CgkJCQkJCTxYTUJNPjEwMTwvWE1CTT4KCQkJCQkJPCEtLSDpobnnm67lkI3np7Ag5a2X5q616ZW/MjAwIC0tPgoJCQkJCQk8WE1NQz48L1hNTUM+CgkJCQkJCTwhLS0g6aG555uu5Y2V5L2NIOWtl+autemVvzEwMCAtLT4KCQkJCQkJPFhNRFc+PC9YTURXPgoJCQkJCQk8IS0tIOinhOagvOWei+WPtyDlrZfmrrXplb8yMDAgLS0+CgkJCQkJCTxHR1hIPjwvR0dYSD4KCQkJCQkJPCEtLSDpobnnm67mlbDph48g5a2X5q616ZW/MjQgLS0+CgkJCQkJCTxYTVNMPjwvWE1TTD4KCQkJCQkJPCEtLSDlkKvnqI7moIfor4Yg5b+F6aG7IOWtl+autemVvzEgMC3kuI3lkKvnqI4gMS3pg73lkKvnqI4gLS0+CgkJCQkJCTxIU0JaPjE8L0hTQlo+CgkJCQkJCTwhLS0g6aG555uu5Y2V5Lu3IOW/hemhuyDlrZfmrrXplb8yNCAtLT4KCQkJCQkJPFhNREo+NS4wMDwvWE1ESj4KCQkJCQkJPCEtLSDpobnnm67ph5Hpop0g5b+F6aG7IOWtl+autemVvzE2IC0tPgoJCQkJCQk8WE1KRT41LjAwPC9YTUpFPgoJCQkJCQk8IS0tIOeojueOhyDlv4Xpobsg5a2X5q616ZW/MTAgMC3lhY3nqI4gLS0+CgkJCQkJCTxTTD4wPC9TTD4KCQkJCQkJPCEtLSDnqI7pop0g5a2X5q616ZW/MjAgLS0+CgkJCQkJCTxTRT48L1NFPgoJCQkJCQk8IS0tIOWkh+eUqOWtl+autSAtLT4KCQkJCQkJPEJZWkQxPjwvQllaRDE+CgkJCQkJCTwhLS0g5aSH55So5a2X5q61IC0tPgoJCQkJCQk8QllaRDI+PC9CWVpEMj4KCQkJCQkJPCEtLSDlpIfnlKjlrZfmrrUgLS0+CgkJCQkJCTxCWVpEMz48L0JZWkQzPgoJCQkJCQk8IS0tIOWkh+eUqOWtl+autSAtLT4KCQkJCQkJPEJZWkQ0PjwvQllaRDQ+CgkJCQkJCTwhLS0g5aSH55So5a2X5q61IC0tPgoJCQkJCQk8QllaRDU+PC9CWVpENT4KCQkJCQk8L0ZQS0pYWF9YTVhYPgoJCQkJPC9GUEtKWFhfWE1YWFM+CgkJCTwvUkVRVUVTVF9GUEtKWFg+")));
        System.out.println(new String(Base64.decodeBase64("CiAgPFJFU1BPTlNFX0ZQWFhYWiBjbGFzcz0iUkVTUE9OU0VfRlBYWFhaIj4KICAgIDxGUFFRTFNIPk1aMDAxMTcwOTE1MDAwMDA0MDAxPC9GUFFRTFNIPgogICAgPEZQX0RNPjAzMTAwMTYwMDQxMTwvRlBfRE0+CiAgICA8RlBfSE0+Mjk3MTIyODU8L0ZQX0hNPgogICAgPEtQUlE+MjAxNzA5MTUxNTQyNTc8L0tQUlE+CiAgICA8S1BMWD4xPC9LUExYPgogICAgPEhKQkhTSkU+NTwvSEpCSFNKRT4KICAgIDxLUEhKU0U+MDwvS1BISlNFPgogICAgPFBERl9VUkwvPgogICAgPFJFVFVSTkNPREU+MDAwMDwvUkVUVVJOQ09ERT4KICAgIDxSRVRVUk5NRVNTQUdFLz4KICA8L1JFU1BPTlNFX0ZQWFhYWj4K")));
//        Matcher matcher = regex.matcher("1705160UA03169864317499229990914");
//        System.out.println(matcher.matches());
    }
}
