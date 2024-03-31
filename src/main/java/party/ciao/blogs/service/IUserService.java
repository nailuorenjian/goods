package party.ciao.blogs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import party.ciao.blogs.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
public interface IUserService extends IService<User> {

    IPage pageCC(IPage<User> page, Wrapper wrapper);
}
