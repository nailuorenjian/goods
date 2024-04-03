package party.ciao.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import party.ciao.blogs.entity.Goodstype;
import party.ciao.blogs.entity.Storage;
import party.ciao.blogs.mapper.GoodstypeMapper;
import party.ciao.blogs.mapper.StorageMapper;
import party.ciao.blogs.service.IGoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements IGoodstypeService {

    @Resource
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageCC(IPage<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.pageCC(page,wrapper);
    }
}
