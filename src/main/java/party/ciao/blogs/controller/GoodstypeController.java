package party.ciao.blogs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import party.ciao.blogs.common.QueryPageParam;
import party.ciao.blogs.common.Result;
import party.ciao.blogs.entity.Goodstype;
import party.ciao.blogs.entity.Storage;
import party.ciao.blogs.service.IGoodstypeService;
import party.ciao.blogs.service.IStorageService;

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
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Autowired
    private IGoodstypeService goodstypeService;

    // 增
    @RequestMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)?Result.suc():Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype) ? Result.suc() : Result.fail();
    }

    // 删
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id){
        return goodstypeService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Storage::getName, name);
        }

        IPage result = goodstypeService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
