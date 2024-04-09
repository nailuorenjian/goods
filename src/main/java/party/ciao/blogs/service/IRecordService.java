package party.ciao.blogs.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import party.ciao.blogs.entity.Goods;
import party.ciao.blogs.entity.Record;
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
public interface IRecordService extends IService<Record> {

    IPage pageCC(IPage<Record> page, Wrapper wrapper);
}
