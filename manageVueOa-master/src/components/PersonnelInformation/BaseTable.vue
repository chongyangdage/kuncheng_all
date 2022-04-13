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
      </div>
      <el-table
        :data="TableData"
        border
        style="width: 100%"
        ref="multipleTable"
     
      >
        <el-table-column align="center" type="index" label="序号" width="120">
        </el-table-column>

        <el-table-column align="center" prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column align="center" prop="sex" label="性别" width="80">
        </el-table-column>
        <el-table-column align="center" prop="ID" label="身份证号" width="200">
        </el-table-column>
        <el-table-column align="center" prop="phone" label="手机号" width="120">
        </el-table-column>
        <el-table-column align="center" prop="address" label="地址">
        </el-table-column>
        <el-table-column
          align="center"
          prop="date"
          label="入职日期"
          width="150"
        >
        </el-table-column>
        <el-table-column align="center" label="操作" width="230">
          <template slot-scope="scope">
            <el-button size="small" @click="WorkEdit(scope.row)"
              >工作信息详情</el-button
            >
            <el-button size="small" @click="payEdit(scope.row)"
              >薪资详情</el-button
            >
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
    <el-dialog title="工作信息详情" :visible.sync="workVisible" width="30%">
      <el-form ref="formWork" :model="formWork" label-width="90px">
        <el-form-item label="状态">
          <el-select
            v-model="formWork.state"
            placeholder="筛选状态"
            class="handle-select mr10"
          >
            <el-option key="1" label="试用" value="广东省"></el-option>
            <el-option key="2" label="正式" value="湖南省"></el-option>
            <el-option key="1" label="待离职" value="广东省"></el-option>
            <el-option key="2" label="已离职" value="湖南省"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工种">
          <el-input v-model="formWork.kind"></el-input>
        </el-form-item>
        <el-form-item label="工作时长">
          <el-input v-model="formWork.time"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 薪资信息弹出框 -->
    <el-dialog title="薪资详情" :visible.sync="payVisible" width="30%">
      <el-form ref="formPay" :model="formPay" label-width="90px">
        <el-form-item label="月工资">
          <el-input v-model="formPay.monthPay"></el-input>
        </el-form-item>
        <el-form-item label="银行卡号">
          <el-input v-model="formPay.payNumber"></el-input>
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
export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      TableData: [
        {
          name: "张锋",
          sex: "男",
          ID: "150404199710100525",
          phone: 15147478858,
          address: "赤峰市巴林左旗临潢路振通商厦东南侧约160米",
          date: "2022.10.23",
        },
      ],
      cur_page: 1,
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      workVisible: false,
      payVisible: false,
      formWork: {},
      formPay: {},
      idx: -1,
    };
  },
  created() {
    this.getData();
  },

  methods: {
    //工作信息详情
    WorkEdit(e) {
      this.workVisible = true;
    },
    //薪资信息详情
    payEdit(e) {
        this.payVisible=true
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
      if (process.env.NODE_ENV === "development") {
        this.url = "/ms/table/list";
      }
      this.$axios
        .post(this.url, {
          page: this.cur_page,
        })
        .then((res) => {
          this.tableData = res.data.list;
        });
    },
    search() {
      this.is_search = true;
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
