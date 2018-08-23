package cn.webank.mumble.mpc.giftcard.intergration.sao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.Charsets;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.framework.common.mapper.JsonMapper;
import cn.webank.mumble.framework.common.utils.MumbleContextUtil;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.CheckReceiverAddressReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.CheckReceiverAddressRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderDetailReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderDetailRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderRouteReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderRouteRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherCheckReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherCheckRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherVerificationReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherVerificationRsp;
import cn.webank.mumble.mpc.giftcard.common.utils.HttpClientUtils;
import cn.webank.mumble.mpc.giftcard.intergration.sao.CallSFSAO;
import com.google.common.hash.Hashing;

@Component("cn.webank.mumble.mpc.giftcard.intergration.sao.CallSFSAO")
public class CallSFSAOImpl implements CallSFSAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CallSFSAOImpl.class);
	private final static JsonMapper JSON_MAPPER = JsonMapper.nonEmptyMapper();

	@Value("${outer.network.proxy.port}")
	private int outProxyPort; //顺风

	@Value("${outer.network.proxy.host}")
	private  String outProxyHost;//顺风
	
	@Value("${proxy.host}")
	private String innerProxyHost; //非码

	@Value("${proxy.port}")
	private int innerProxyPort;//非码
	
	@Value("${mpc.sf.sk}")
	private String sk; 
	
	@Value("${mpc.sf.ak}")
	private String ak; 
	
	@Value("${mpc.sf.orderFrom}")
	private String orderFrom; 

	@Value("${mpc.sf.checkReceiverAddressUrl}")
	private String checkReceiverAddressUrl; 
	
	@Value("${mpc.sf.orderBookUrl}")
	private String orderBookUrl; 
	
	@Value("${mpc.sf.queryOrderDetailUrl}")
	private String queryOrderDetailUrl; 
	
	@Value("${mpc.sf.queryOrderRouteUrl}")
	private String queryOrderRouteUrl; 
	
	@Value("${mpc.freeMud.secretkey}")
	private String secretkey; 
	
	@Value("${mpc.freeMud.requestAccount:1205}")
	private String requestAccount; 
	
	@Value("${mpc.freeMud.voucherCheckUrl}")
	private String voucherCheckUrl; 
	
	@Value("${mpc.freeMud.voucherVerificationUrl}")
	private String voucherVerificationUrl; 
	
	@Value("${mpc.freeMud.storeNumber}")
	private String storeNumber; 
	
    @Value("${system.env:}")
    private String envfcd;
	
	@SuppressWarnings("unchecked")
	@Override
	public CheckReceiverAddressRsp checkReceiverAddress(CheckReceiverAddressReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.checkReceiverAddress:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setOrderFrom(orderFrom);
		req.setAk(ak);
		TreeMap<String,String> signMap=JSON_MAPPER.fromJson(JSON_MAPPER.toJson(req), TreeMap.class); //默认升序排序
		req.setSign(sign(signMap));
		String params = JSON_MAPPER.toJson(req);
		String result = submit(checkReceiverAddressUrl,params);
		return result == null ? null : JSON_MAPPER.fromJson(result, CheckReceiverAddressRsp.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderBookRsp orderBook(OrderBookReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.orderBook:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setOrderFrom(orderFrom);
		req.setAk(ak);
		TreeMap<String,String> signMap=JSON_MAPPER.fromJson(JSON_MAPPER.toJson(req), TreeMap.class); //默认升序排序
		req.setSign(sign(signMap));
		String params = JSON_MAPPER.toJson(req);
		String result = submit(orderBookUrl,params);
		return result == null ? null : JSON_MAPPER.fromJson(result, OrderBookRsp.class); 
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryOrderDetailRsp queryOrderDetail(QueryOrderDetailReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.queryOrderDetail:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setOrderFrom(orderFrom);
		req.setAk(ak);
		TreeMap<String,String> signMap=JSON_MAPPER.fromJson(JSON_MAPPER.toJson(req), TreeMap.class); //默认升序排序
		req.setSign(sign(signMap));
		String url = String.format(queryOrderDetailUrl,req.getThirdOrderId(), req.getOrderFrom(),req.getAk(),req.getSign()); 
		String result = submitGet(url); 
		return result == null ? null : JSON_MAPPER.fromJson(result, QueryOrderDetailRsp.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryOrderRouteRsp queryOrderRoute(QueryOrderRouteReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.queryOrderRoute:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setOrderFrom(orderFrom);
		req.setAk(ak);
		TreeMap<String,String> signMap=JSON_MAPPER.fromJson(JSON_MAPPER.toJson(req), TreeMap.class); //默认升序排序
		req.setSign(sign(signMap));
		String url = String.format(queryOrderRouteUrl,req.getThirdOrderId(), req.getOrderFrom(),req.getAk(),req.getSign()); 
		String result = submitGet(url); 
		return result == null ? null : JSON_MAPPER.fromJson(result, QueryOrderRouteRsp.class);
	}

	@Override
	public VoucherCheckRsp voucherCheck(VoucherCheckReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.voucherCheck:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setRequestAccount(requestAccount);
		req.setStoreNumber(storeNumber);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(storeNumber);
		stringBuffer.append(requestAccount);
		stringBuffer.append(secretkey);
		String signParam = signParam(stringBuffer.toString());
		req.setSignParam(signParam);
		String params = JSON_MAPPER.toJson(req);
		String result = submitFreeMud(voucherCheckUrl,params);
		return result == null ? null : JSON_MAPPER.fromJson(result, VoucherCheckRsp.class);
	}

	@Override
	public VoucherVerificationRsp voucherVerification(VoucherVerificationReq req, BizErrors bizErrors) {
		LOGGER.info("CallSFSAOImpl.voucherVerification:{}={}", MumbleContextUtil.getBizSeqNo(), JSON_MAPPER.toJson(req));
		req.setRequestAccount(requestAccount);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(req.getStoreNumber());
		stringBuffer.append(requestAccount);
		stringBuffer.append(secretkey);
		String signParam = signParam(stringBuffer.toString());
		
		req.setSignParam(signParam);
		String params = JSON_MAPPER.toJson(req);
		String result = submitFreeMud(voucherVerificationUrl,params);
		return result == null ? null : JSON_MAPPER.fromJson(result, VoucherVerificationRsp.class);
	}
   
	private String sign(Map<String, String> values) {
		values.put("sk", sk); //添加一组键值sk=xxx
		if (!StringUtils.isEmpty(values.get("sku_list"))) {
			String skuList=JSON_MAPPER.toJson(values.get("sku_list"));
			values.put("sku_list", skuList);
		}
		String waitSign=JSON_MAPPER.toJson(values);
		LOGGER.info("wait for sign(sha256) asc:{} ",waitSign);
		return Hashing.sha256().hashString(waitSign, Charsets.UTF_8).toString();
	}
	
	private String signParam (String signParam) {
		LOGGER.info("wait for sign MD5:{} ",signParam);
		return Hashing.md5().hashString(signParam, Charsets.UTF_8).toString().toUpperCase();
	}
	
	@SuppressWarnings("unchecked")
	private String submit(String url, String params) {
		LOGGER.info("submit send<>url: {} {}",url,params);
		String result = null;
		CloseableHttpResponse response = null;
		HttpPost httpPost=null;
		CloseableHttpClient httpClient=null;
		try {
			//update  2017-12-14生产该走代理
			if ("sit".equals(envfcd)) {
                LOGGER.info("submit sit");
                httpClient = HttpClientUtils.acceptsHttpClient(null, 0); // vpn访问：update  
            } else {
                LOGGER.info("innerProxyHost={} ,innerProxyPort={}", innerProxyHost, innerProxyPort);
                httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient(innerProxyHost, innerProxyPort);   
            }
			httpPost = new HttpPost(url);
			
			UrlEncodedFormEntity uefEntity;
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			Map<String, Object> postMap = JSON_MAPPER.fromJson(params, Map.class);
			for (String key : postMap.keySet()) {
				Object object = postMap.get(key);
				if (object instanceof List) {
					formparams.add(new BasicNameValuePair(key, JSON_MAPPER.toJson(object)));
				} else {
					formparams.add(new BasicNameValuePair(key, (String) object));
				}
			}
            
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(uefEntity);
			LOGGER.info("submit send formparams: {} {}",url,JSON_MAPPER.toJson(formparams));
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				result = EntityUtils.toString(response.getEntity(), Consts.UTF_8).trim();
				LOGGER.info("CallSFSAOImpl.submit response :{}", result);
			} else {
				LOGGER.info("CallSFSAOImpl.submit response  error:{} StatusCode={} ", response, response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			LOGGER.error("httpClient submit fail:", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					LOGGER.error("response close fail", e);
				}
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return result;
	}
	
	private String submitGet(String url) {
		LOGGER.info("submitGet send<>url:{} ",url);
		String result = null;
		CloseableHttpResponse response=null;
		HttpGet httpGet=null;
		CloseableHttpClient httpClient=null;
		try {
			//update  2017-12-14生产该走代理
			if ("sit".equals(envfcd)) {
                LOGGER.info("submitGet sit");
                httpClient = HttpClientUtils.acceptsHttpClient(null, 0); // vpn访问：update  
            } else {
                LOGGER.info("innerProxyHost={} ,innerProxyPort={}", innerProxyHost, innerProxyPort);
                httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient(innerProxyHost, innerProxyPort);   
            }
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				result = EntityUtils.toString(response.getEntity(), Consts.UTF_8).trim();
                if(result.startsWith("array")){
                	result =result.substring(result.indexOf(")")+1, result.length());
                }
				LOGGER.info("CallSFSAOImpl.submit response :{}", result);
			} else {
				LOGGER.info("CallSFSAOImpl.submit response  error:{} StatusCode={} ", response, response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			LOGGER.error("httpClient submit fail:", e);;
		}finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					LOGGER.error("response close fail", e);
				}
			}
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}
		return result;
	}
	
	private String submitFreeMud(String url,String params) {
		LOGGER.info("submitFreeMud send<>url: {} {}",url,params);
		String result = null;
		CloseableHttpResponse response=null;
		HttpPost httpPost=null;
		CloseableHttpClient httpClient;
		try {
			
			if ("sit".equals(envfcd)) {
                LOGGER.info("outerProxyHost={} ,outerProxyPort={}", outProxyHost, outProxyPort);
                httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient(outProxyHost, outProxyPort);   
            } else {
                LOGGER.info("innerProxyHost={} ,innerProxyPort={}", innerProxyHost, innerProxyPort);
                httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient(innerProxyHost, innerProxyPort);   
            }
		
			httpPost = new HttpPost(url);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");  
			httpPost.setHeader(HttpHeaders.ACCEPT, "application/json;charset=utf-8"); 
			
			StringEntity reqentity = new StringEntity(params, Consts.UTF_8);
			httpPost.setEntity(reqentity);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				result = EntityUtils.toString(response.getEntity(), Consts.UTF_8).trim();
				LOGGER.info("CallSFSAOImpl.submitFreeMud response :{}", result);
			} else {
				LOGGER.info("CallSFSAOImpl.submitFreeMud response  error:{} StatusCode={} ", response, response
						.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			LOGGER.error("httpClient submitFreeMud fail:", e);;
		}finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					LOGGER.error("response close fail", e);
				}
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return result;
	}
	
}
