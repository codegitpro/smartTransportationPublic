package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.TousufankuiEntity;
import com.entity.view.TousufankuiView;

import com.service.TousufankuiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;

/**
 * 投诉反馈
 * 后端接口
 * @author 
 * @email 
 * @date 2023-06-25 08:13:01
 */
@RestController
@RequestMapping("/tousufankui")
public class TousufankuiController {
    @Autowired
    private TousufankuiService tousufankuiService;




    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TousufankuiEntity tousufankui, 
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			tousufankui.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<TousufankuiEntity> ew = new EntityWrapper<TousufankuiEntity>();


		PageUtils page = tousufankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tousufankui), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TousufankuiEntity tousufankui, 
		HttpServletRequest request){
        EntityWrapper<TousufankuiEntity> ew = new EntityWrapper<TousufankuiEntity>();

		PageUtils page = tousufankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tousufankui), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TousufankuiEntity tousufankui){
       	EntityWrapper<TousufankuiEntity> ew = new EntityWrapper<TousufankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tousufankui, "tousufankui")); 
        return R.ok().put("data", tousufankuiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TousufankuiEntity tousufankui){
        EntityWrapper< TousufankuiEntity> ew = new EntityWrapper< TousufankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tousufankui, "tousufankui")); 
		TousufankuiView tousufankuiView =  tousufankuiService.selectView(ew);
		return R.ok("查询投诉反馈成功").put("data", tousufankuiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TousufankuiEntity tousufankui = tousufankuiService.selectById(id);
        return R.ok().put("data", tousufankui);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TousufankuiEntity tousufankui = tousufankuiService.selectById(id);
        return R.ok().put("data", tousufankui);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TousufankuiEntity tousufankui, HttpServletRequest request){
    	tousufankui.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tousufankui);

        tousufankuiService.insert(tousufankui);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TousufankuiEntity tousufankui, HttpServletRequest request){
    	tousufankui.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tousufankui);

        tousufankuiService.insert(tousufankui);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody TousufankuiEntity tousufankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tousufankui);
        tousufankuiService.updateById(tousufankui);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<TousufankuiEntity> list = new ArrayList<TousufankuiEntity>();
        for(Long id : ids) {
            TousufankuiEntity tousufankui = tousufankuiService.selectById(id);
            tousufankui.setSfsh(sfsh);
            tousufankui.setShhf(shhf);
            list.add(tousufankui);
        }
        tousufankuiService.updateBatchById(list);
        return R.ok();
    }
    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tousufankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
