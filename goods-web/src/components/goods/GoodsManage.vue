<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" @keyup.enter.native="loadPost" placeholder="input..." suffix-icon="el-icon-search" style="width: 200px"></el-input>

      <el-select style="margin-left: 5px" v-model="storage" filterable placeholder="请选择storage">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-select style="margin-left: 5px" v-model="goodstype" filterable placeholder="请选择goodstype">
        <el-option
            v-for="item in goodsTypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button style="margin-left: 5px" @click="loadPost">select</el-button>
      <el-button @click="resetParam">reset</el-button>
      <el-button @click="add">add</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="goods名" width="110">
      </el-table-column>
      <el-table-column prop="remark" label="remark名" width="110">
      </el-table-column>

      <el-table-column prop="storage" label="storage" width="110" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodsType" label="goodsType" width="110" :formatter="formartGoodsType">
      </el-table-column>
      <el-table-column prop="count" label="count" width="110">
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

        <el-form-item label="storage" prop="storage">
          <el-col :span="20">
            <el-select v-model="form.storage" filterable placeholder="请选择storage">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="goodsType" prop="goodsType">
          <el-col :span="20">
            <el-select v-model="form.goodsType" filterable placeholder="请选择goodsType">
              <el-option
                  v-for="item in goodsTypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="count" prop="count">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.count"></el-input>
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
  name: "GoodsManage",
  data() {
    let checkCount = (rule, value, callback) => {
      if (value > 999999) {
        callback(new Error('数量输入过大'));
      } else {
        callback();
      }
    };

    return {
      storageData:[],
      goodsTypeData:[],
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name:'',
      storage:'',
      goodstype:'',
      centerDialogVisible:false,
      form:{
        name:'',
        no:'',
        remark:'',
        storage:'',
        goodsType:'',
        count:''
      },

      // 提交表单时候的校验
      rules: {
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        storage: [
          {required: true, message: '请选择仓库', trigger: 'blur'}
        ],
        goodsType: [
          {required: true, message: '请选择分类', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数字', trigger: "blur"},
          {validator: checkCount, trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    // 把仓库的数据赋给 goods
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id == row.storage
      })

      return temp && temp.name
    },

    // 把仓库类型的数据赋给 goods
    formartGoodsType(row){
      let temp = this.goodsTypeData.find(item=>{
        return item.id == row.storage
      })

      return temp && temp.name
    },

    // reset数据
    resetForm() {
      this.$refs.form.resetFields();
    },

    // 删除数据
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl + '/goods/delete?id=' + id).then(res => res.data).then(res => {
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
        this.form.storage = row.storage
        this.form.goodsType = row.goodsType
        this.form.count = row.count
      })

    },

    // 添加button 打开dialog
    add(){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm()

        this.form.id = ''
      })
    },

    doSave() {
      this.$axios.post(this.$httpUrl + '/goods/save', this.form).then(res => res.data).then(res => {
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
      this.$axios.post(this.$httpUrl + '/goods/update', this.form).then(res => res.data).then(res => {
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
      this.$axios.post(this.$httpUrl + '/goods/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        // 查询时候的传参
        param:{
          name:this.name,
          storage:this.storage+'',
          goodsType:this.goodstype+''
        }
      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200){
          this.tableData = res.data;
          this.total = res.total
        }
      })

    },

    loadStorage(){
      this.$axios.get(this.$httpUrl + '/storage/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200){
          this.storageData = res.data;
        }

      })
    },

    loadGoodsType(){
      this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200){
          this.goodsTypeData = res.data;
        }

      })
    },

    // 重置搜索栏
    resetParam(){
      this.name ='',
          this.goodstype='',
          this.storage=''
      this.loadPost();
    },
  },

  // 打开页面 自动执行
  created() {
    this.loadPost();
    this.loadStorage();
    this.loadGoodsType();
  }

}
</script>


<style scoped>

</style>