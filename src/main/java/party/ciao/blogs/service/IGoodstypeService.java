package party.ciao.blogs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import party.ciao.blogs.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import party.ciao.blogs.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
public interface IGoodstypeService extends IService<Goodstype> {

    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);

}
