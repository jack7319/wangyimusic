package com.bizideal.mn.biz.impl;

import com.bizideal.mn.biz.BaseBiz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName BaseBiz
 * @Description TODO(通用业务层的的实现类)
 * @Author 
 * @Date 
 * @param <T>
 */
public abstract class BaseBizImpl<T> implements BaseBiz<T> {
	
	@Autowired
	private Mapper<T> mapper;

	@Override
	public T queryById(int id) {
		// TODO Auto-generated method stub
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() {
		// TODO Auto-generated method stub
		return this.mapper.select(null);
	}

	@Override
	public T queryOne(T record) {
		// TODO Auto-generated method stub
		return this.mapper.selectOne(record);
	}

	@Override
	public List<T> queryListByWhere(T record) {
		// TODO Auto-generated method stub
		return this.mapper.select(record);
	}

	@Override
	public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record) {
		// TODO Auto-generated method stub
		// 设置分页参数
		PageHelper.startPage(page, rows);
		List<T> list = this.mapper.select(record);
		return new PageInfo<T>(list);
	}

	@Override
	@Transactional
	public Integer save(T t) {
		// TODO Auto-generated method stub
		return this.mapper.insert(t);
	}

	@Override
	@Transactional
	public Integer saveSelective(T t) {
		// TODO Auto-generated method stub
		return this.mapper.insertSelective(t);
	}

	@Override
	@Transactional
	public Integer update(T t) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKey(t);
	}

	@Override
	@Transactional
	public Integer updateSelective(T t) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	@Transactional
	public Integer deleteById(Integer id) {
		// TODO Auto-generated method stub
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public Integer deleteByIds(Class<T> clazz, String property,
			List<Object> values) {
		// TODO Auto-generated method stub
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return this.mapper.deleteByExample(example);
	}

	@Override
	@Transactional
	public Integer deleteByWhere(T record) {
		// TODO Auto-generated method stub
		return this.mapper.delete(record);
	}
}
