<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          ><i class="el-icon-tickets"></i> 人员信息</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-select
          v-model="select_cate"
          placeholder="筛选工种"
          class="handle-select mr10"
        >
          <el-option key="1" label="广东省" value="广东省"></el-option>
          <el-option key="2" label="湖南省" value="湖南省"></el-option>
        </el-select>
        <el-input
          v-model="select_word"
          placeholder="筛选姓名"
          class="handle-input mr10"
        ></el-input>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button
          type="primary"
          icon="delete"
          class="handle-del mr10"
          @click="add"
          >添加人员</el-button
        >
      </div>

      <el-table
        :data="TableData"
        border
        style="width: 100%"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column align="center" type="index" label="序号" width="120">
        </el-table-column>

        <el-table-column
          align="center"
          prop="username"
          label="姓名"
          width="120"
        >
        </el-table-column>
        <el-table-column align="center" prop="sex" label="性别" width="80">
        </el-table-column>
        <el-table-column align="center" prop="carID" label="身份证号" width="200">
        </el-table-column>
        <el-table-column align="center" prop="phone" label="手机号" width="120">
        </el-table-column>
        <el-table-column align="center" prop="address" label="地址">
        </el-table-column>

        <el-table-column
       
          align="center"
          prop="createdTime"
          label="入职日期"
          width="150"
        >
        </el-table-column>
        <el-table-column align="center" label="操作" width="230">
          <template slot-scope="scope">
            <el-button size="small" @click="Edit(scope.row)">修改</el-button>
            <el-button size="small" @click="Dele(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="1000"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 工作信息弹出框 -->
    <el-dialog :title="'人员信息' + text" :visible.sync="Visible" width="40%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="状态">
          <el-select
            v-model="form.state"
            placeholder="筛选状态"
            class="handle-select mr10"
          >
            <el-option key="1" label="试用" value="1"></el-option>
            <el-option key="2" label="正式" value="2"></el-option>
            <el-option key="1" label="待离职" value="3"></el-option>
            <el-option key="2" label="已离职" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工种">
          <el-select
            v-model="form.kind"
            placeholder="筛选工种"
            class="handle-select mr10"
          >
            <el-option key="1" label="木工" value="1"></el-option>
            <el-option key="2" label="瓦工" value="2"></el-option>
            <el-option key="1" label="钢筋工" value="3"></el-option>
            <el-option key="2" label="工长" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入职时间">
          <el-date-picker
            value-format="yyyy-MM-dd"
            v-model="form.createdTime"
            type="date"
            placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select
            v-model="form.sex"
            placeholder="性别"
            class="handle-select mr10"
          >
            <el-option key="1" label="男" value="1"></el-option>
            <el-option key="2" label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.carID"></el-input>
        </el-form-item>

        <el-form-item label="手机号">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="月工资">
          <el-input v-model="form.monthPay"></el-input>
        </el-form-item>
        <el-form-item label="银行卡号">
          <el-input v-model="form.carNum"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addPeople } from "@/api/addPeople/index";
import { findPeople,delePeople } from "@/api/user_all/index";
export default {
  name: "basetable",
  data() {
    return {
      text: "",
      url: "./static/vuetable.json",
      TableData: [
   
      ],
      cur_page: 1,
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      Visible: false,
      payVisible: false,
      form: {},
      formPay: {},
      idx: -1,
    };
  },
  created() {
    this.getData();
  },
  computed: {
    data() {
      return this.tableData.filter((d) => {
        let is_del = false;
        for (let i = 0; i < this.del_list.length; i++) {
          if (d.name === this.del_list[i].name) {
            is_del = true;
            break;
          }
        }
        if (!is_del) {
          if (
            d.address.indexOf(this.select_cate) > -1 &&
            (d.name.indexOf(this.select_word) > -1 ||
              d.address.indexOf(this.select_word) > -1)
          ) {
            return d;
          }
        }
      });
    },
  },
  methods: {
    //添加人员
    add() {
      this.text = "添加";
      this.Visible = true;
    },
    //人员修改
    Edit(e) {
      this.text = "修改";
      this.Visible = true;
    },
    //删除人员
    Dele(e) {
   
        this.$confirm('此操作将永久删除该人员, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
 
             delePeople({id:e.id}).then(res=>{
            if(res.state==200){
                 this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getData()
            }
          })
       
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });  
           });        

    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      findPeople().then((res) => {
        if (res.state == 200) {
          this.TableData = res.data;
        } else {
          this.$message.error("查询失败");
        }
        console.log(res);
      });
    },
    search() {
      this.is_search = true;
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleEdit(index, row) {
      this.idx = index;
      const item = this.tableData[index];
      this.form = {
        name: item.name,
        date: item.date,
        address: item.address,
      };
      this.editVisible = true;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    delAll() {
      const length = this.multipleSelection.length;
      let str = "";
      this.del_list = this.del_list.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error("删除了" + str);
      this.multipleSelection = [];
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 保存编辑
    saveEdit() {
      if (this.text == "添加") {
        addPeople(this.form).then((res) => {
          if (res.state == 200) {
            this.$message.success("添加成功");
              this.getData()
            this.Visible = false;
          } else {
            this.$message.error("添加失败");
          }
          console.log(res);
        });
      }
    },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
</style>
