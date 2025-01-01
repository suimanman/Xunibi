<template>
  <div class="container">
    <div class="search">
      <el-input v-model="searchQuery" placeholder="请输入团队名称查询" style="width: 200px"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="search" icon="el-icon-zoom-in">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset" icon="el-icon-refresh-left">重置</el-button>
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <div class="table">
      <el-table :data="paginatedTeams" @selection-change="handleSelectionChange" border>
        <el-table-column prop="teamId" label="团队ID" sortable></el-table-column>
        <el-table-column prop="teamName" label="团队名称"></el-table-column>
        <el-table-column prop="virtualCoins" label="虚拟币数量"></el-table-column>
        <el-table-column prop="creationDate" label="入住日期"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.teamId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
        v-if="teams.length > 0"
        background
        layout="prev, pager, next,sizes, total, jumper"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        :total="teams.length"
        @current-change="handlePageChange"
        @size-change="handlePageSizeChange"
    >
    </el-pagination>

    <el-dialog :title="editMode ? '编辑团队' : '新增团队'" :visible.sync="dialogVisible">
      <el-form :model="newTeam">
        <el-form-item label="团队名称">
          <el-input v-model="newTeam.teamName"></el-input>
        </el-form-item>
        <el-form-item label="虚拟币数量">
          <el-input v-model="newTeam.virtualCoins" type="number"></el-input>
        </el-form-item>
        <el-form-item label="入住日期">
          <el-date-picker v-model="newTeam.creationDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveTeam">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchQuery: "",
      teams: [], // 所有团队数据
      currentPage: 1, // 当前页
      pageSize: 10, // 每页显示数量
      paginatedTeams: [], // 当前页显示的数据
      dialogVisible: false,
      newTeam: {
        teamId: null,
        teamName: "",
        virtualCoins: 0,
        creationDate: null,
      },
      editMode: false,
    };
  },
  created() {
    this.load(); // 页面加载时获取数据
  },
  mounted() {
    this.load();
  },
  methods: {

    // 搜索团队
    search() {
      if (!this.searchQuery) {
        this.$message.error("请输入团队名字查询");
        return;
      }

      // 在团队数据中筛选包含输入内容的记录
      const filteredTeams = this.teams.filter((team) =>
          team.teamName.toString().includes(this.searchQuery)
      );

      if (filteredTeams.length === 0) {
        this.$message.warning("未找到匹配的团队");
      }

      // 更新团队显示数据
      this.paginatedTeams = filteredTeams.slice(0, this.pageSize);
      this.currentPage = 1; // 重置当前页
    },

    // 重置搜索
    reset() {
      this.searchQuery = ""; // 清空搜索框
      this.handlePageChange(1); // 恢复到第一页的分页数据
    },


    // 加载数据
    async load() {
      try {
        const response = await axios.get("http://localhost:8080/team/list");
        if (response.status === 200 && response.data) {
          this.teams = response.data; // 更新所有团队数据
          this.handlePageChange(this.currentPage); // 确保分页正确显示
        }
      } catch (error) {
        console.error("数据加载失败:", error);
      }
    },
    handlePageSizeChange(newSize){
      this.pageSize = newSize;
      this.currentPage = 1; // 重置到第一页
      this.updateDisplayedData();

    },
    updateDisplayedData(){
      const start = (this.currentPage - 1) * this.pageSize;
      const end =  start + this.pageSize
      // 确保分页的队列正确显示数据
      this.paginatedTeams = this.teams.slice(start, end);
    },
    // 分页处理
    handlePageChange(page) {
      this.currentPage = page;
      this.updateDisplayedData()

      // 打印分页数据，调试时使用
      console.log("currentPage:", this.currentPage);
      console.log("start:", start, "end:", end);
      console.log("paginatedTeams:", this.paginatedTeams);
    },
    // 添加团队
    handleAdd() {
      this.newTeam = { teamId: null, teamName: "", virtualCoins: 0, creationDate: null };
      this.editMode = false;
      this.dialogVisible = true;
    },
    // 删除团队
    async del(teamId) {
      try {
        await axios.delete(`http://localhost:8080/team/delete/${teamId}`);
        this.$message.success("删除成功");
        this.load();
      } catch (error) {
        console.error("删除失败:", error);
        this.$message.error("删除失败");
      }
    },
    // 编辑团队
    handleEdit(row) {
      this.newTeam = { ...row };
      this.editMode = true;
      this.dialogVisible = true;
    },
    // 保存团队
    async saveTeam() {
      try {
        if (this.editMode) {
          await axios.put(`http://localhost:8080/team/update/${this.newTeam.teamId}`, this.newTeam);
          this.$message.success("更新成功");
        } else {
          await axios.post("http://localhost:8080/team/create", this.newTeam);
          this.$message.success("新增成功");
        }
        this.dialogVisible = false;
        this.load();
      } catch (error) {
        console.error("保存失败:", error);
        this.$message.error("保存失败");
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

.table {
  margin-bottom: 20px;
}
</style>