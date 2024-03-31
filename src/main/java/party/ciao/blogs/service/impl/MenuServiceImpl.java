package party.ciao.blogs.service.impl;

import party.ciao.blogs.entity.Menu;
import party.ciao.blogs.mapper.MenuMapper;
import party.ciao.blogs.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
