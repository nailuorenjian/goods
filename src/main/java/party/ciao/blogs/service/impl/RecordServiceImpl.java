package party.ciao.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import party.ciao.blogs.entity.Goods;
import party.ciao.blogs.entity.Record;
import party.ciao.blogs.entity.Storage;
import party.ciao.blogs.mapper.GoodsMapper;
import party.ciao.blogs.mapper.RecordMapper;
import party.ciao.blogs.mapper.StorageMapper;
import party.ciao.blogs.service.IRecordService;
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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Resource
    private RecordMapper recordMapper;
    @Override
    public IPage pageCC(IPage<Record> page, Wrapper wrapper) {

        return recordMapper.pageCC(page,wrapper);
    }

}
