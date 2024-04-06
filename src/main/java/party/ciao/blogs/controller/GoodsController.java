package party.ciao.blogs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.ciao.blogs.common.QueryPageParam;
import party.ciao.blogs.common.Result;
import party.ciao.blogs.entity.Goods;
import party.ciao.blogs.service.IGoodsService;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    // 增
    @RequestMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.suc():Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodsService.updateById(goods) ? Result.suc() : Result.fail();
    }

    // 删
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){
        return goodsService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();

        // 查询时候，需要的传参
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodsType");
        String storage = (String) param.get("storage");


        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
        }


        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            lambdaQueryWrapper.eq(Goods::getGoodsType, goodstype);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            lambdaQueryWrapper.eq(Goods::getStorage, storage);
        }


        IPage result = goodsService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }


}
