<template>
  <div class="container">
    <div class="header">
      <el-input v-model="searchQuery" placeholder="请输入团队名称查看" style="width: 250px"></el-input>
      <el-button type="info" plain @click="search" icon="el-icon-zoom-in">查询</el-button>
      <el-button type="warning" plain @click="reset" icon="el-icon-refresh-left">重置</el-button>
    </div>

    <div class="table-wrapper">
      <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
        <el-table-column prop="dutyId" label="值班记录 ID" align="center" width="100px" />
        <el-table-column prop="teamId" label="团队 ID" align="center" />
        <el-table-column prop="teamName" label="团队名称" align="center" />
        <el-table-column prop="dutyDate" label="值班日期" align="center" />
        <el-table-column prop="coinAwarded" label="奖励虚拟币" align="center" />
        <el-table-column prop="description" label="描述" align="center" />
        <el-table-column prop="status" label="状态" align="center" />
        <el-table-column prop="image" label="图片" align="center">
          <template v-slot="scope">
            <img :src="scope.row.image" alt="值班图片" style="max-width: 100px; max-height: 100px;" />
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="sizes, prev, pager, next, total"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @current-change="handlePageChange"
          @size-change="handleSizeChange">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      searchQuery: "", // 查询字段
      tableData: [], // 当前页的数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      total: 0, // 总记录数
      dialogVisible: false, // 弹窗显示控制
      selectedRow: null, // 当前选中的行
    };
  },
  mounted() {
    this.getAllDutyRecords(); // 初次加载获取所有记录
  },
  methods: {
    async getAllDutyRecords() {
      try {
        const [DutyListResponse, teamListResponse] = await Promise.all([
          axios.get("http://localhost:8080/dutyrecords/all"),
          axios.get("http://localhost:8080/team/list"),
        ]);
        if (DutyListResponse.data.code === 200 && teamListResponse.status === 200) {
          const fullData = DutyListResponse.data.data.map((item) => {
            const team = teamListResponse.data.find((t) => t.teamId === item.teamId);
            return {
              ...item,
              teamName: team ? team.teamName : "未知",
            };
          });

          this.total = fullData.length; // 更新总记录数
          this.tableData = this.paginate(fullData); // 分页显示数据
        }
      } catch (error) {
        console.error("获取待审核列表失败:", error);
        this.$message.error("获取待审核列表失败，请稍后重试");
      }
    },
    paginate(data) {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      return data.slice(start, end);
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.getAllDutyRecords();
    },
    handleSizeChange(size) {
      this.pageSize = size;
      this.currentPage = 1; // 重置到第一页
      this.getAllDutyRecords();
    },
    search() {
      if (!this.searchQuery) {
        this.$message.warning("请输入团队名称查询");
        return;
      }

      // 根据团队名称过滤数据
      this.tableData = this.tableData.filter(item =>
          item.teamName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    reset() {
      this.searchQuery = "";
      this.currentPage = 1; // 重置到第一页
      this.getAllDutyRecords();
    },
    openDialog(row) {
      this.selectedRow = row;
      this.dialogVisible = true;
    },
    handlePass() {
      if (!this.selectedRow) return;

      axios
          .put("http://localhost:8080/dutyrecords/update", {
            dutyId: this.selectedRow.dutyId,
            status: "已通过",
          })
          .then((response) => {
            if (response.data.code === 200) {
              this.$message.success("审核通过成功");
              this.selectedRow.status = "已通过";
              this.dialogVisible = false;
            } else {
              this.$message.error(response.data.msg || "审核失败");
            }
          })
          .catch((error) => {
            console.error("审核失败:", error);
            this.$message.error("审核失败，请稍后重试");
          });
    },
    handleNoPass(row) {
      axios
          .put("http://localhost:8080/dutyrecords/update", {
            dutyId: row.dutyId,
            status: "未通过",
          })
          .then((response) => {
            if (response.data.code === 200) {
              this.$message.success("审核不通过成功");
              row.status = "未通过";
            } else {
              this.$message.error(response.data.msg || "操作失败");
            }
          })
          .catch((error) => {
            console.error("操作失败:", error);
            this.$message.error("操作失败，请稍后重试");
          });
    },
  },
};
</script>

<style scoped>
.container {
  padding: 20px;
  background-color: #f9f9f9;
}

.header {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.header .el-input {
  max-width: 300px;
}

.table-wrapper {
  border: 1px solid #e0e0e0;
  border-radius: 5px;
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
}
</style>