package party.ciao.blogs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import party.ciao.blogs.common.Result;
import party.ciao.blogs.entity.Menu;
import party.ciao.blogs.entity.User;
import party.ciao.blogs.service.IMenuService;
import party.ciao.blogs.service.IUserService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kuw
 * @since 2024-03-26
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/list")
    public Result list(@RequestParam String roleId){
        List<Menu> list = menuService.lambdaQuery().like(Menu::getMenuRight,roleId).list();
        return Result.suc(list);
    }

}
