<template>
  <div style="display: flex; line-height: 60px">
    <div style="margin-top: 8px;">
      <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
    </div>

    <div style="flex: 1;text-align: center;font-size: 34px;">
      <span>welcome to the back side</span>
    </div>
    <el-dropdown>
      <span>{{user.name}}</span>
      <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },

  props:{
    icon:String
  },
  methods: {
    toUser() {
      console.log("hello");
      this.$router.push('/Home');
    },
    logOut() {

      this.$confirm('logOut, continue?', 'attention', {
        confirmButtonText: 'sure',
        cancelButtonText: 'cancel',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: 'logout!'
        });

        //跳转到主页
        this.$router.push('/');
        // 删除数据
        sessionStorage.clear();

      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'cancel'
        });
      });
    },
    collapse(){
      this.$emit('doCollapse')
    }
  },
  created() {
    this.$router.push("/Home");
  }
}
</script>

<style scoped>

</style>