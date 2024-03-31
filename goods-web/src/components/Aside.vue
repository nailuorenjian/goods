<template>

  <el-menu
      style="height: 100%;"
      :collapse="isCollapse"
      :collapse-transition="false"
      default-active="/home"
      router>

    <el-menu-item index="/home">
      <i class="el-icon-house"></i>
      <span slot="title">home</span>
    </el-menu-item>

    <el-menu-item :index="'/'+item.menuClick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuIcon"></i>
      <span slot="title">{{ item.menuName }}</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      // isCollapse:false
      // menu:[ // 模拟的菜单数据
      //   {
      //     menuClick:'Admin',
      //     menuName:'管理员管理',
      //     menuIcon:'el-icon-s-custom'
      //   },
      //   {
      //     menuClick:'User',
      //     menuName:'user管理',
      //     menuIcon:'el-icon-user-solid'
      //   },
      // ]

    }
  },

  computed:{
      "menu":{
        get(){
          // 数据库中保存的菜单数据
          return this.$store.state.menu
        }
      }
  },

  props: {
    isCollapse:Boolean
  },
}
</script>

<style scoped>


</style>