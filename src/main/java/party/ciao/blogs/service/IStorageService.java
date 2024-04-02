package party.ciao.blogs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import party.ciao.blogs.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import party.ciao.blogs.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
public interface IStorageService extends IService<Storage> {

    IPage pageCC(IPage<Storage> page, Wrapper wrapper);

}
