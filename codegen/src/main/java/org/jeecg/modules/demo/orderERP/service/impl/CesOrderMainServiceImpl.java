package org.jeecg.modules.demo.orderERP.service.impl;

import org.jeecg.modules.demo.orderERP.entity.CesOrderMain;
import org.jeecg.modules.demo.orderERP.entity.CesOrderGoods;
import org.jeecg.modules.demo.orderERP.entity.CesOrderCustomer;
import org.jeecg.modules.demo.orderERP.mapper.CesOrderGoodsMapper;
import org.jeecg.modules.demo.orderERP.mapper.CesOrderCustomerMapper;
import org.jeecg.modules.demo.orderERP.mapper.CesOrderMainMapper;
import org.jeecg.modules.demo.orderERP.service.ICesOrderMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 商城订单表
 * @Author: jeecg-boot
 * @Date:   2023-02-18
 * @Version: V1.0
 */
@Service
public class CesOrderMainServiceImpl extends ServiceImpl<CesOrderMainMapper, CesOrderMain> implements ICesOrderMainService {

	@Autowired
	private CesOrderMainMapper cesOrderMainMapper;
	@Autowired
	private CesOrderGoodsMapper cesOrderGoodsMapper;
	@Autowired
	private CesOrderCustomerMapper cesOrderCustomerMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		cesOrderGoodsMapper.deleteByMainId(id);
		cesOrderCustomerMapper.deleteByMainId(id);
		cesOrderMainMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			cesOrderGoodsMapper.deleteByMainId(id.toString());
			cesOrderCustomerMapper.deleteByMainId(id.toString());
			cesOrderMainMapper.deleteById(id);
		}
	}
	
}
