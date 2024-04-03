package party.ciao.blogs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import party.ciao.blogs.entity.Goodstype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import party.ciao.blogs.entity.Storage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {

    IPage pageCC(IPage<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);


}
