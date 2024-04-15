<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" @keyup.enter.native="loadPost" placeholder="input..." suffix-icon="el-icon-search" style="width: 200px"></el-input>
      <el-button style="margin-left: 5px" @click="loadPost">select</el-button>
      <el-button @click="resetParam">reset</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名" width="180">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" width="180">
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类" width="180">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="180">
      </el-table-column>
      <el-table-column prop="username" label="申请人" width="180">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>

      <el-table-column prop="operate" label="operate" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="mod(scope.row)">edit</el-button>
          <el-popconfirm
              title="确定删除吗？"
              style="margin-left: 5px" @confirm="del(scope.row.id)">
            <el-button slot="reference" size="mini" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[3, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "RecordManage",
  data() {

    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name:'',
      centerDialogVisible:false,
      form:{
        id: '',
        name: '',
        storage: '',
        goodstype: '',
        count: '',
        remark: ''
      },

    }
  },
  methods: {
    formatStorage(row) {
      let temp = this.storageData.find(item => {
        return item.id == row.storage
      })

      return temp && temp.name
    },
    formatGoodstype(row) {
      let temp = this.goodstypeData.find(item => {
        return item.id == row.goodstype
      })

      return temp && temp.name
    },

    // 数据校验
    resetForm() {
      this.$refs.form.resetFields();
    },

    // 分页相关
    handleSizeChange(val){
      this.pageSize=val;
      this.loadPost();
    },
    handleCurrentChange(val){
      this.pageNum=val;
      this.loadPost();
    },

    // 获取list
    loadPost() {
      this.$axios.post(this.$httpUrl + '/record/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name: this.name,
          goodstype: this.goodstype + '',
          storage: this.storage + '',
          roleId: this.user.roleId + '',
          userId: this.user.id + ''
        }
      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200){
          this.tableData = res.data;
          this.total = res.total

        }
      })
    },

    // 重置搜索栏
    resetParam(){
      this.name =''
      this.storage = ''
      this.goodstype = ''
      this.loadPost();
    },
  },

  // 打开页面 自动执行
  created() {
    this.loadPost();
    this.loadStorage()
    this.loadGoodstype()
  }

}
</script>


<style scoped>

</style>