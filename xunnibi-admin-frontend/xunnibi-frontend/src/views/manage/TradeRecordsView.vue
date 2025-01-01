<template>
  <div class="container">
    <div class="header">
      <el-input v-model="searchQuery" placeholder="请输入团队名称" style="width: 250px"></el-input>
      <el-button type="info" plain @click="search" icon="el-icon-zoom-in">查询</el-button>
      <el-button type="warning" plain @click="reset" icon="el-icon-refresh-left">重置</el-button>
    </div>

    <div class="table-wrapper">
      <el-table ref="filterTable" :data="displayedData" style="width: 100%" border>
        <el-table-column prop="transactionId" label="id" align="center"></el-table-column>
        <el-table-column prop="transactionDate" label="交易日期" align="center"></el-table-column>
        <el-table-column prop="transactionType" label="交易类型" align="center"></el-table-column>
        <el-table-column prop="teamName" label="团队名称" align="center"></el-table-column>
        <el-table-column prop="coinAmount" label="交易虚拟币数量" align="center"></el-table-column>
        <el-table-column prop="description" label="交易内容" align="center"></el-table-column>
      </el-table>

      <el-pagination background layout="prev, pager, next, sizes, total, jumper" :current-page="currentPage"
                     :page-size="pageSize" :total="total" @current-change="handlePageChange"
                     @size-change="handlePageSizeChange"></el-pagination>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchQuery: null, // 搜索关键字
      tableData: [], // 原始数据
      displayedData: [], // 当前页显示的数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      total: 0, // 数据总条数
    };
  },
  mounted() {
    this.getAchievementList(); // 加载数据
  },
  methods: {
    // 获取数据
    async getAchievementList() {
      try {
        const [achievementListResponse, teamListResponse] = await Promise.all([
          axios.get("http://localhost:8080/records/virtualCoinRecords"),
          axios.get("http://localhost:8080/team/list"),
        ]);
        if (achievementListResponse.data.code === 200 && teamListResponse.status === 200) {
          const achievementList = achievementListResponse.data.data.map((item) => {
            const team = teamListResponse.data.find((t) => t.teamId === item.teamId);
            return {
              ...item,
              teamName: team ? team.teamName : "未知",
            };
          });
          this.tableData = achievementList;
          this.total = achievementList.length; // 设置总条目数
          this.updateDisplayedData(); // 计算当前页数据
        }
      } catch (error) {
        console.error("获取数据失败:", error);
        this.$message.error("获取数据失败，请稍后重试");
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
      this.tableData = filteredData;
      this.updateDisplayedData(); // 更新显示数据
    },

    // 重置查询
    reset() {
      this.searchQuery = null; // 清空搜索关键字
      this.getAchievementList(); // 重新加载数据
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