package com.imooc.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

import static com.baomidou.mybatisplus.core.metadata.OrderItem.asc;
import static com.baomidou.mybatisplus.core.metadata.OrderItem.desc;
import static com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank;
import static com.imooc.util.StringUtil.humpToUnderline;

/**
 * 查询条件
 *
 * @author baoxj
 * @date 2021/1/28
 */
@Data
@ApiModel(description = "查询条件")
public abstract class BasePageQuery {

	@ApiModelProperty("当前页")
	@Positive
	private int current = 1;

	@ApiModelProperty("每页的数量")
	@Positive
	private int size = 10;

	@ApiModelProperty("正序")
	private String asc;

	@ApiModelProperty("倒序")
	private String desc;

	public <T> Page<T> getPage(OrderItem... defaultItems) {
		Page<T> page = new Page<>(current, size);
		if (isNotBlank(asc)) {
			page.addOrder(asc(humpToUnderline(asc)));
		} else if (isNotBlank(desc)) {
			page.addOrder(desc(humpToUnderline(desc)));
		} else {
			page.addOrder(defaultItems);
		}
		return page;
	}

	/**
	 * 生成QueryWrapper
	 * @param <T> 类型
	 * @return QueryWrapper
	 */
	public abstract <T> QueryWrapper<T> wrapperQuery();

}
