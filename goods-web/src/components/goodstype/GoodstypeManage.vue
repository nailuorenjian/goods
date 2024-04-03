<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" @keyup.enter.native="loadPost" placeholder="input..." suffix-icon="el-icon-search" style="width: 200px"></el-input>
      <el-button style="margin-left: 5px" @click="loadPost">select</el-button>
      <el-button @click="resetParam">reset</el-button>
      <el-button @click="add">add</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="goods姓名" width="110">
      </el-table-column>
      <el-table-column prop="remark" label="remark名" width="110">
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

    <!-- dialog -->
    <el-dialog
        title="input data"
        :visible.sync="centerDialogVisible"
        width="50%"
        center>

      <el-form ref="form" :rules="rules" :model="form" label-width="100px">

        <el-form-item label="goods name" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="remark" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "GoodstypeManage",
  data() {

    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name:'',
      centerDialogVisible:false,
      form:{
        name:'',
        no:'',
        remark:''
      },

      // 校验
      rules: {
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    // 数据校验
    resetForm() {
      this.$refs.form.resetFields();
    },

    // 删除数据
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl + '/goodstype/delete?id=' + id).then(res => res.data).then(res => {
        if (res.code == 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadPost()
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      });
    },

    // 修改数据
    mod(row){
      console.log(row);
      // 打开dialog
      this.centerDialogVisible = true

      // 赋值到表单
      this.$nextTick(() => {
        //赋值到表单
        this.form.id = row.id
        this.form.name = row.name
        this.form.remark = row.remark
      })

    },

    // 添加button 打开dialog
    add(){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm()
      })
    },

    doSave() {
      this.$axios.post(this.$httpUrl + '/goodstype/save', this.form).then(res => res.data).then(res => {
        if (res.code == 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      });
    },

    doMod() {
      this.$axios.post(this.$httpUrl + '/goodstype/update', this.form).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.resetForm();
          this.loadPost();
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });

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
      this.$axios.post(this.$httpUrl + '/goodstype/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name
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
      this.loadPost();
    },
  },

  // 打开页面 自动执行
  created() {
    this.loadPost();
  }

}
</script>


<style scoped>

</style>