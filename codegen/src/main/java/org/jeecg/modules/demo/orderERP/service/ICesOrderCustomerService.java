package org.jeecg.modules.demo.orderERP.service;

import org.jeecg.modules.demo.orderERP.entity.CesOrderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单客户
 * @Author: jeecg-boot
 * @Date:   2023-02-18
 * @Version: V1.0
 */
public interface ICesOrderCustomerService extends IService<CesOrderCustomer> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<CesOrderCustomer>
   */
	public List<CesOrderCustomer> selectByMainId(String mainId);
}
