package org.jeecg.modules.demo.orderERP.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.orderERP.entity.CesOrderGoods;
import org.jeecg.modules.demo.orderERP.entity.CesOrderCustomer;
import org.jeecg.modules.demo.orderERP.entity.CesOrderMain;
import org.jeecg.modules.demo.orderERP.service.ICesOrderMainService;
import org.jeecg.modules.demo.orderERP.service.ICesOrderGoodsService;
import org.jeecg.modules.demo.orderERP.service.ICesOrderCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 商城订单表
 * @Author: jeecg-boot
 * @Date:   2023-02-18
 * @Version: V1.0
 */
@Api(tags="商城订单表")
@RestController
@RequestMapping("/orderERP/cesOrderMain")
@Slf4j
public class CesOrderMainController extends JeecgController<CesOrderMain, ICesOrderMainService> {

	@Autowired
	private ICesOrderMainService cesOrderMainService;

	@Autowired
	private ICesOrderGoodsService cesOrderGoodsService;

	@Autowired
	private ICesOrderCustomerService cesOrderCustomerService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param cesOrderMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商城订单表-分页列表查询")
	@ApiOperation(value="商城订单表-分页列表查询", notes="商城订单表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CesOrderMain>> queryPageList(CesOrderMain cesOrderMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CesOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(cesOrderMain, req.getParameterMap());
		Page<CesOrderMain> page = new Page<CesOrderMain>(pageNo, pageSize);
		IPage<CesOrderMain> pageList = cesOrderMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param cesOrderMain
     * @return
     */
    @AutoLog(value = "商城订单表-添加")
    @ApiOperation(value="商城订单表-添加", notes="商城订单表-添加")
    //@RequiresPermissions("orderERP:ces_order_main:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CesOrderMain cesOrderMain) {
        cesOrderMainService.save(cesOrderMain);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param cesOrderMain
     * @return
     */
    @AutoLog(value = "商城订单表-编辑")
    @ApiOperation(value="商城订单表-编辑", notes="商城订单表-编辑")
    //@RequiresPermissions("orderERP:ces_order_main:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody CesOrderMain cesOrderMain) {
        cesOrderMainService.updateById(cesOrderMain);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "商城订单表-通过id删除")
    @ApiOperation(value="商城订单表-通过id删除", notes="商城订单表-通过id删除")
    //@RequiresPermissions("orderERP:ces_order_main:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        cesOrderMainService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "商城订单表-批量删除")
    @ApiOperation(value="商城订单表-批量删除", notes="商城订单表-批量删除")
    //@RequiresPermissions("orderERP:ces_order_main:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.cesOrderMainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    //@RequiresPermissions("orderERP:ces_order_main:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CesOrderMain cesOrderMain) {
        return super.exportXls(request, cesOrderMain, CesOrderMain.class, "商城订单表");
    }

    /**
     * 导入
     * @return
     */
    //@RequiresPermissions("orderERP:ces_order_main:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CesOrderMain.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-订单商品-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "订单商品-通过主表ID查询")
	@ApiOperation(value="订单商品-通过主表ID查询", notes="订单商品-通过主表ID查询")
	@GetMapping(value = "/listCesOrderGoodsByMainId")
    public Result<IPage<CesOrderGoods>> listCesOrderGoodsByMainId(CesOrderGoods cesOrderGoods,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<CesOrderGoods> queryWrapper = QueryGenerator.initQueryWrapper(cesOrderGoods, req.getParameterMap());
        Page<CesOrderGoods> page = new Page<CesOrderGoods>(pageNo, pageSize);
        IPage<CesOrderGoods> pageList = cesOrderGoodsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param cesOrderGoods
	 * @return
	 */
	@AutoLog(value = "订单商品-添加")
	@ApiOperation(value="订单商品-添加", notes="订单商品-添加")
	@PostMapping(value = "/addCesOrderGoods")
	public Result<String> addCesOrderGoods(@RequestBody CesOrderGoods cesOrderGoods) {
		cesOrderGoodsService.save(cesOrderGoods);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param cesOrderGoods
	 * @return
	 */
	@AutoLog(value = "订单商品-编辑")
	@ApiOperation(value="订单商品-编辑", notes="订单商品-编辑")
	@RequestMapping(value = "/editCesOrderGoods", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editCesOrderGoods(@RequestBody CesOrderGoods cesOrderGoods) {
		cesOrderGoodsService.updateById(cesOrderGoods);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单商品-通过id删除")
	@ApiOperation(value="订单商品-通过id删除", notes="订单商品-通过id删除")
	@DeleteMapping(value = "/deleteCesOrderGoods")
	public Result<String> deleteCesOrderGoods(@RequestParam(name="id",required=true) String id) {
		cesOrderGoodsService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单商品-批量删除")
	@ApiOperation(value="订单商品-批量删除", notes="订单商品-批量删除")
	@DeleteMapping(value = "/deleteBatchCesOrderGoods")
	public Result<String> deleteBatchCesOrderGoods(@RequestParam(name="ids",required=true) String ids) {
	    this.cesOrderGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportCesOrderGoods")
    public ModelAndView exportCesOrderGoods(HttpServletRequest request, CesOrderGoods cesOrderGoods) {
		 // Step.1 组装查询条件
		 QueryWrapper<CesOrderGoods> queryWrapper = QueryGenerator.initQueryWrapper(cesOrderGoods, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<CesOrderGoods> pageList = cesOrderGoodsService.list(queryWrapper);
		 List<CesOrderGoods> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "订单商品");
		 mv.addObject(NormalExcelConstants.CLASS, CesOrderGoods.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单商品报表", "导出人:" + sysUser.getRealname(), "订单商品"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importCesOrderGoods/{mainId}")
    public Result<?> importCesOrderGoods(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<CesOrderGoods> list = ExcelImportUtil.importExcel(file.getInputStream(), CesOrderGoods.class, params);
				 for (CesOrderGoods temp : list) {
                    temp.setOrderMainId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 cesOrderGoodsService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-订单商品-end----------------------------------------------*/

    /*--------------------------------子表处理-订单客户-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "订单客户-通过主表ID查询")
	@ApiOperation(value="订单客户-通过主表ID查询", notes="订单客户-通过主表ID查询")
	@GetMapping(value = "/listCesOrderCustomerByMainId")
    public Result<IPage<CesOrderCustomer>> listCesOrderCustomerByMainId(CesOrderCustomer cesOrderCustomer,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<CesOrderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(cesOrderCustomer, req.getParameterMap());
        Page<CesOrderCustomer> page = new Page<CesOrderCustomer>(pageNo, pageSize);
        IPage<CesOrderCustomer> pageList = cesOrderCustomerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param cesOrderCustomer
	 * @return
	 */
	@AutoLog(value = "订单客户-添加")
	@ApiOperation(value="订单客户-添加", notes="订单客户-添加")
	@PostMapping(value = "/addCesOrderCustomer")
	public Result<String> addCesOrderCustomer(@RequestBody CesOrderCustomer cesOrderCustomer) {
		cesOrderCustomerService.save(cesOrderCustomer);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param cesOrderCustomer
	 * @return
	 */
	@AutoLog(value = "订单客户-编辑")
	@ApiOperation(value="订单客户-编辑", notes="订单客户-编辑")
	@RequestMapping(value = "/editCesOrderCustomer", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editCesOrderCustomer(@RequestBody CesOrderCustomer cesOrderCustomer) {
		cesOrderCustomerService.updateById(cesOrderCustomer);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单客户-通过id删除")
	@ApiOperation(value="订单客户-通过id删除", notes="订单客户-通过id删除")
	@DeleteMapping(value = "/deleteCesOrderCustomer")
	public Result<String> deleteCesOrderCustomer(@RequestParam(name="id",required=true) String id) {
		cesOrderCustomerService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单客户-批量删除")
	@ApiOperation(value="订单客户-批量删除", notes="订单客户-批量删除")
	@DeleteMapping(value = "/deleteBatchCesOrderCustomer")
	public Result<String> deleteBatchCesOrderCustomer(@RequestParam(name="ids",required=true) String ids) {
	    this.cesOrderCustomerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportCesOrderCustomer")
    public ModelAndView exportCesOrderCustomer(HttpServletRequest request, CesOrderCustomer cesOrderCustomer) {
		 // Step.1 组装查询条件
		 QueryWrapper<CesOrderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(cesOrderCustomer, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<CesOrderCustomer> pageList = cesOrderCustomerService.list(queryWrapper);
		 List<CesOrderCustomer> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "订单客户");
		 mv.addObject(NormalExcelConstants.CLASS, CesOrderCustomer.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单客户报表", "导出人:" + sysUser.getRealname(), "订单客户"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importCesOrderCustomer/{mainId}")
    public Result<?> importCesOrderCustomer(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<CesOrderCustomer> list = ExcelImportUtil.importExcel(file.getInputStream(), CesOrderCustomer.class, params);
				 for (CesOrderCustomer temp : list) {
                    temp.setOrderMainId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 cesOrderCustomerService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-订单客户-end----------------------------------------------*/




}
