package party.ciao.blogs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.ciao.blogs.common.QueryPageParam;
import party.ciao.blogs.common.Result;
import party.ciao.blogs.entity.Menu;
import party.ciao.blogs.entity.User;
import party.ciao.blogs.service.IMenuService;
import party.ciao.blogs.service.IUserService;

import java.util.HashMap;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    // 增
    @RequestMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fail();
    }

    // 改
    @RequestMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    // 新增或修改
    @RequestMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    // 删
    @RequestMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    // 删
    @RequestMapping("/del")
    public Result delete(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();


        if (list.size() > 0){
            // 获取登陆的第一条数据
            User user1 = (User) list.get(0);
            // 获取动态路由 根据user的roleId 查询menu表中，MenuRight的数据
            List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuRight,user1.getRoleId()).list();

            // 把user的list数据和menu的list数据放入 HashMap
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);

            return Result.suc(res);
        }
        return Result.fail();
    }


    // 模糊查询
    @RequestMapping("/listP")
    public List listP(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.like(User::getName,user.getName());
        return userService.list(wrapper);
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    @RequestMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println("name===" + (String) param.get("name"));

        //        Page<User> page = new Page(1,2);
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String gender = (String) param.get("gender");
        String roleId = (String) param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }

        if (StringUtils.isNotBlank(gender)) {
            lambdaQueryWrapper.eq(User::getGender, gender);
        }
        if (StringUtils.isNotBlank(roleId)) {
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }

        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

}
