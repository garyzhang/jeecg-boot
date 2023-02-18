package org.jeecg.modules.demo.orderERP.service;

import org.jeecg.modules.demo.orderERP.entity.CesOrderGoods;
import org.jeecg.modules.demo.orderERP.entity.CesOrderCustomer;
import org.jeecg.modules.demo.orderERP.entity.CesOrderMain;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 商城订单表
 * @Author: jeecg-boot
 * @Date:   2023-02-18
 * @Version: V1.0
 */
public interface ICesOrderMainService extends IService<CesOrderMain> {

	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
