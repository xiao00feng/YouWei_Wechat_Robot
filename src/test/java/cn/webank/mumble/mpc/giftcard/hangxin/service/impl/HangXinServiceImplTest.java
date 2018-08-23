package cn.webank.mumble.mpc.giftcard.hangxin.service.impl;

import cn.webank.mumble.framework.common.mapper.XmlMapper;
import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.hangxin.common.dto.UserAuthorizeInvoiceEvent;
import cn.webank.mumble.mpc.giftcard.hangxin.common.dto.interfaceStruct.Data;
import cn.webank.mumble.mpc.giftcard.hangxin.common.dto.interfaceStruct.Interface;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import static org.testng.Assert.*;

/**
 * @author ctrlzhang on 2017/5/26.
 */
public class HangXinServiceImplTest extends JunitBaseTest {

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.hangxin.service.HangXinService")
    HangXinServiceImpl service;

    @Test
    public void testInvoice() throws Exception {
        UserAuthorizeInvoiceEvent req = new UserAuthorizeInvoiceEvent();
        service.invoice(req);
    }

    @Test
    public void testXml() {
        String xml  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<interface xmlns:schemaLocation=\"http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"WLFP1.0\">\n" +
                " <globalInfo>\n" +
                "  <interfaceCode>ECXML.FPXZ</interfaceCode>\n" +
                "  <requestCode>admin</requestCode>\n" +
                "  <requestTime>2017-07-05 19:46:51</requestTime>\n" +
                "  <passWord>1B2M2Y8AsgTpgAmY7PhCfg==</passWord>\n" +
                " </globalInfo>\n" +
                " <returnStateInfo>\n" +
                "  <returnCode>0000</returnCode>\n" +
                "  <returnMessage />\n" +
                " </returnStateInfo>\n" +
                " <Data>\n" +
                "  <dataDescription>\n" +
                "   <zipCode>0</zipCode>\n" +
                "   <encryptCode />\n" +
                "  </dataDescription>\n" +
                "  <content>CiAgPFJFU1BPTlNFX0ZQWFhYWiBjbGFzcz0iUkVTUE9OU0VfRlBYWFhaIj4KICAgIDxGUFFRTFNIPmFkbWluMTcwNzA1MDAwMDA1MDAxPC9GUFFRTFNIPgogICAgPEZQX0RNPjAzMTAwMTYwMDMxMTwvRlBfRE0+CiAgICA8RlBfSE0+MjAwMjQ3OTk8L0ZQX0hNPgogICAgPEtQUlE+MjAxNzA3MDUxNzUzNDY8L0tQUlE+CiAgICA8S1BMWD4xPC9LUExYPgogICAgPEhKQkhTSkU+MC4wMTwvSEpCSFNKRT4KICAgIDxLUEhKU0U+MDwvS1BISlNFPgogICAgPFBERl9VUkwvPgogICAgPFJFVFVSTkNPREU+MDAwMDwvUkVUVVJOQ09ERT4KICAgIDxSRVRVUk5NRVNTQUdFLz4KICA8L1JFU1BPTlNFX0ZQWFhYWj4K</content>\n" +
                " </Data>\n" +
                "</interface>";

        XmlMapper xmlMapper = new XmlMapper();
        Interface data = xmlMapper.fromXml(xml, Interface.class);
//        try {
//            JAXBContext context = JAXBContext.newInstance(Interface.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
//            Interface data = (Interface) unmarshaller.unmarshal(inputStream);
            LOGGER.info(data.toString());
            LOGGER.info(new String(org.apache.commons.codec.binary.Base64.decodeBase64("5oql5paH6Zeo5bqX5L+h5oGv5LiK5LiL5LiN5Yy56YWN")));
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
    }
}