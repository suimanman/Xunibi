<template>
  <div class="container">
    <div class="header">
      <el-input v-model="searchQuery" placeholder="请输入团队名称" style="width: 250px"></el-input>
      <el-button type="primary" plain @click="search" icon="el-icon-zoom-in">查询</el-button>
      <el-button type="warning" plain @click="reset" icon="el-icon-refresh-left">重置</el-button>
      <el-button type="info" plain @click="exportFile" icon="el-icon-download">导出</el-button>
    </div>

    <div class="table-wrapper">
      <el-table ref="filterTable" :data="displayedData" style="width: 100%" border>
        <el-table-column prop="achievementId" label="ID" align="center" width="100px" />
        <el-table-column prop="material" label="审核材料" align="left" />
        <el-table-column prop="achievementType" label="审核材料类型" align="center" width="150px" />
        <el-table-column prop="teamId" label="团队ID" align="center" width="100px" />
        <el-table-column prop="teamName" label="团队名称" align="center" width="100px" />
        <el-table-column prop="status" label="审核状态" align="center" width="100px" />
      </el-table>

      <el-pagination
          background
          layout="prev, pager, next, sizes, total, jumper"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @current-change="handlePageChange"
          @size-change="handlePageSizeChange"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      teamList: [], // 团队数据列表
      searchQuery: null, // 搜索关键字
      tableData: [], // 原始数据
      displayedData: [], // 当前页显示的数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      total: 0, // 数据总条数
    };
  },
  mounted() {
    this.getAchievementList(); // 初始化加载数据
  },
  methods: {
    // 获取待审核列表
    async getAchievementList() {
      try {
        const [achievementListResponse, teamListResponse] = await Promise.all([
          axios.get("http://localhost:8080/records/achieveRecords"),
          axios.get("http://localhost:8080/team/list"),
        ]);
        if (achievementListResponse.data.code === 200 && teamListResponse.status === 200) {
          this.tableData = achievementListResponse.data.data.map((item) => {
            const team = teamListResponse.data.find((t) => t.teamId === item.teamId);
            return {
              ...item,
              material: item.description,
              teamName: team ? team.teamName : "未知",
            };
          });
          this.total = this.tableData.length; // 设置总条目数
          this.updateDisplayedData(); // 初始化当前页数据
        }
      } catch (error) {
        console.error("获取待审核列表失败:", error);
        this.$message.error("获取待审核列表失败，请稍后重试");
      }
    },

    // 更新显示的数据
    updateDisplayedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.displayedData = this.tableData.slice(start, end);
    },

    // 处理页码变化
    handlePageChange(newPage) {
      this.currentPage = newPage;
      this.updateDisplayedData();
    },

    // 处理每页条数变化
    handlePageSizeChange(newSize) {
      this.pageSize = newSize;
      this.currentPage = 1; // 重置到第一页
      this.updateDisplayedData();
    },

    // 查询功能
    search() {
      if (!this.searchQuery) {
        this.$message.warning("请输入团队名称查询");
        return;
      }
      const filteredData = this.tableData.filter(item =>
          item.teamName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
      this.total = filteredData.length; // 更新总条目数
      this.currentPage = 1; // 重置页码
      this.displayedData = filteredData.slice(0, this.pageSize); // 更新显示数据
    },

    // 重置查询
    reset() {
      this.searchQuery = null;
      this.currentPage = 1;
      this.updateDisplayedData();
    },

    // 导出文件
    async exportFile() {
      try {
        const response = await axios.get("http://localhost:8080/export/achieve", {
          responseType: "arraybuffer", // 返回文件数据
        });
        const blob = new Blob([response.data], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "Achievements_Report.xlsx";
        link.click();
      } catch (error) {
        console.error("导出报表失败:", error);
        this.$message.error("导出报表失败，请稍后再试");
      }
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
