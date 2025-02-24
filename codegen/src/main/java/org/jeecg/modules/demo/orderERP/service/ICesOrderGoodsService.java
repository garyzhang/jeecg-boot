package org.jeecg.modules.demo.orderERP.service;

import org.jeecg.modules.demo.orderERP.entity.CesOrderGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单商品
 * @Author: jeecg-boot
 * @Date:   2023-02-18
 * @Version: V1.0
 */
public interface ICesOrderGoodsService extends IService<CesOrderGoods> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<CesOrderGoods>
   */
	public List<CesOrderGoods> selectByMainId(String mainId);
}
