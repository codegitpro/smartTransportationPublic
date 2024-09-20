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

import com.entity.JiaotonggonggaoEntity;
import com.entity.view.JiaotonggonggaoView;

import com.service.JiaotonggonggaoService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;

/**
 * 交通公告
 * 后端接口
 * @author 
 * @email 
 * @date 2023-06-25 08:13:01
 */
@RestController
@RequestMapping("/jiaotonggonggao")
public class JiaotonggonggaoController {
    @Autowired
    private JiaotonggonggaoService jiaotonggonggaoService;




    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiaotonggonggaoEntity jiaotonggonggao, 
		HttpServletRequest request){

        EntityWrapper<JiaotonggonggaoEntity> ew = new EntityWrapper<JiaotonggonggaoEntity>();


		PageUtils page = jiaotonggonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiaotonggonggao), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiaotonggonggaoEntity jiaotonggonggao, 
		HttpServletRequest request){
        EntityWrapper<JiaotonggonggaoEntity> ew = new EntityWrapper<JiaotonggonggaoEntity>();

		PageUtils page = jiaotonggonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiaotonggonggao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiaotonggonggaoEntity jiaotonggonggao){
       	EntityWrapper<JiaotonggonggaoEntity> ew = new EntityWrapper<JiaotonggonggaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiaotonggonggao, "jiaotonggonggao")); 
        return R.ok().put("data", jiaotonggonggaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiaotonggonggaoEntity jiaotonggonggao){
        EntityWrapper< JiaotonggonggaoEntity> ew = new EntityWrapper< JiaotonggonggaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiaotonggonggao, "jiaotonggonggao")); 
		JiaotonggonggaoView jiaotonggonggaoView =  jiaotonggonggaoService.selectView(ew);
		return R.ok("查询交通公告成功").put("data", jiaotonggonggaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiaotonggonggaoEntity jiaotonggonggao = jiaotonggonggaoService.selectById(id);
		jiaotonggonggao.setClicknum(jiaotonggonggao.getClicknum()+1);
		jiaotonggonggao.setClicktime(new Date());
		jiaotonggonggaoService.updateById(jiaotonggonggao);
        return R.ok().put("data", jiaotonggonggao);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiaotonggonggaoEntity jiaotonggonggao = jiaotonggonggaoService.selectById(id);
		jiaotonggonggao.setClicknum(jiaotonggonggao.getClicknum()+1);
		jiaotonggonggao.setClicktime(new Date());
		jiaotonggonggaoService.updateById(jiaotonggonggao);
        return R.ok().put("data", jiaotonggonggao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiaotonggonggaoEntity jiaotonggonggao, HttpServletRequest request){
    	jiaotonggonggao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiaotonggonggao);

        jiaotonggonggaoService.insert(jiaotonggonggao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiaotonggonggaoEntity jiaotonggonggao, HttpServletRequest request){
    	jiaotonggonggao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiaotonggonggao);

        jiaotonggonggaoService.insert(jiaotonggonggao);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiaotonggonggaoEntity jiaotonggonggao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiaotonggonggao);
        jiaotonggonggaoService.updateById(jiaotonggonggao);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiaotonggonggaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,JiaotonggonggaoEntity jiaotonggonggao, HttpServletRequest request,String pre){
        EntityWrapper<JiaotonggonggaoEntity> ew = new EntityWrapper<JiaotonggonggaoEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        
        params.put("order", "desc");
		PageUtils page = jiaotonggonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiaotonggonggao), params), params));
        return R.ok().put("data", page);
    }









}
