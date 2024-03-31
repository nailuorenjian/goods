<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" @keyup.enter.native="loadPost" placeholder="input..." suffix-icon="el-icon-search" style="width: 200px"></el-input>
      <el-select style="margin-left: 5px" v-model="gender" filterable placeholder="请选择gender">
        <el-option
            v-for="item in genders"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 5px" @click="loadPost">select</el-button>
      <el-button @click="resetParam">reset</el-button>
      <el-button @click="add">add</el-button>
    </div>
  <el-table :data="tableData">
    <el-table-column prop="id" label="ID" width="60">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="110">
    </el-table-column>
    <el-table-column prop="no" label="role">
    </el-table-column>
    <el-table-column prop="age" label="age">
    </el-table-column>
    <el-table-column prop="gender" label="gender">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.gender === 1 ? 'primary' : 'success'"
        disable-transitions>{{scope.row.gender === 1 ? 'male' : 'female'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="password" label="password">
    </el-table-column>
    <el-table-column prop="phone" label="phone">
    </el-table-column>
    <el-table-column prop="roleId" label="roleId">
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

        <el-form-item label="name" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="role" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="age" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">male</el-radio>
            <el-radio label="0">female</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="password" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="phone" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="roleId" prop="RoleId">
          <el-col :span="20">
            <el-input v-model="form.roleId"></el-input>
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
  name: "Main",
  data() {

    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error('年龄输入过大'));
      } else {
        callback();
      }
    };

    // 重复数据判断
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback();
        } else {
          callback(new Error('账号已经存在'));
        }
      })
    };

    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name:'',
      gender:'',
      genders:[
        {
          value: '1',
          label: 'male'
        }, {
          value: '0',
          label: 'female'
        }
      ],
      centerDialogVisible:false,
      form:{
        name:'',
        no:'',
        password:'',
        age:'',
        gender:'0',
        phone:'',
        roleId:'2'
      },

      // 校验
      rules: {
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        no: [
          {required: true, message: '请输入name', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入name', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true, message: "手机号不能为空", trigger: "blur"},
          {min: 1, max: 8, message: '长度在 1 到 8 的数字', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: "请输入1到6位的数字", trigger: "blur"}
        ]
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
      this.$axios.get(this.$httpUrl + '/user/del?id=' + id).then(res => res.data).then(res => {
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
        this.form.no = row.no
        this.form.name = row.name
        this.form.password = row.password + ''
        this.form.age = row.age + ''
        this.form.gender = row.gender + ''
        this.form.phone = row.phone
        this.form.roleId = row.roleId
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
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
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
      this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
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
      this.$axios.post(this.$httpUrl + '/user/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          gender:this.gender
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
      this.gender=''
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