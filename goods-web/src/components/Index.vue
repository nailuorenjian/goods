<template>
  <el-container  class="layout-container" style="border: 1px solid #eee">
    <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246); margin-left: -1px;">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px">
        <Header @doCollapse="doCollapse" :icon ="icon"></Header>
      </el-header>

      <el-main style="height: 100%">
<!--        <Main></Main>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import Main from "@/components/Main";
export default {
  name: "Index",
  components: {Header, Aside},
  data() {
    return {
      isCollapse: false,
      aside_width: '200px',
      icon: 'el-icon-s-fold',
    };
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse;
      if (!this.isCollapse){
        // 展开
        this.icon='el-icon-s-fold';
        this.aside_width = '200px';
      }else {
        // close
        this.aside_width = '64px';
        this.icon='el-icon-s-unfold';
      }
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 0px;
}

.el-aside {
  color: #333;
}
</style>