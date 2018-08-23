package cn.webank.mumble.mpc.giftcard.intergration.sao;

import cn.webank.mumble.framework.common.dto.BizErrors;
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

/**
 * Created by v_wbzytang on 2017/8/22.
 */
public interface CallSFSAO {
	

    /**
     * 检测地址能否配送
     * @param req
     * @param bizErrors
     * @return
     */
	public CheckReceiverAddressRsp checkReceiverAddress(CheckReceiverAddressReq req,BizErrors bizErrors);
	
	/**
	 * 顺风下单
	 * @param req
	 * @param bizErrors
	 * @return
	 */
	public OrderBookRsp orderBook(OrderBookReq req,BizErrors bizErrors);
	
	/**
	 * 查询顺风下单信息
	 * @param req
	 * @param bizErrors
	 * @return
	 */
	public QueryOrderDetailRsp queryOrderDetail(QueryOrderDetailReq req,BizErrors bizErrors);
	
	/**
	 * 查询顺风订单在途信息
	 * @param req
	 * @param bizErrors
	 * @return
	 */
	public QueryOrderRouteRsp queryOrderRoute(QueryOrderRouteReq req,BizErrors bizErrors);
	
	/**
	 * 券校验接口
	 * @param req
	 * @param bizErrors
	 * @return
	 */
	public VoucherCheckRsp voucherCheck(VoucherCheckReq req,BizErrors bizErrors);
	
	/**
	 * 券核销
	 * @param req
	 * @param bizErrors
	 * @return
	 */
	public VoucherVerificationRsp voucherVerification(VoucherVerificationReq req,BizErrors bizErrors);

}
