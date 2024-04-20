import vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter} from "../router";
import createPersistedState from 'vuex-persistedstate';
vue.use(Vuex)

function addNewRoute(menuList) {
    console.log(menuList)
    let routes = router.options.routes
    console.log(routes)

    routes.forEach(routeItem => {
        if (routeItem.path == "/Index") {
            menuList.forEach(menu => {
                let childRoute = {
                    path: '/' + menu.menuClick,
                    name: menu.menuName,
                    meta: {
                        title: menu.menuName
                    },
                    component: () => import('../components/' + menu.menuComponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state:{
        menu:[]
    },
    mutations:{
        setMenu(state,menuList){
            state.menu = menuList
            // 添加路由
            addNewRoute(menuList)
        }
    },
    getters:{
        getMenu(state){
            return state.menu;
        }
    },
    // 插件，防止刷新后，丢失菜单数据
    plugins: [createPersistedState()]
})
