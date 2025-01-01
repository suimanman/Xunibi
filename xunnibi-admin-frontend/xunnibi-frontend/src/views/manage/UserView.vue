<template>
    <div>
        <div class="search">
            <el-input v-model="searchQuery" placeholder="请输入用户名称查询" style="width: 200px"></el-input>
            <el-button type="info" plain style="margin-left: 10px" @click="search" icon="el-icon-zoom-in">查询</el-button>
            <el-button type="warning" plain style="margin-left: 10px" @click="reset"
                icon="el-icon-refresh-left">重置</el-button>
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </div>
      <div class="table">
        <el-table :data="paginatedUsers" @selection-change="handleSelectionChange" border>
          <el-table-column prop="userId" label="用户ID" sortable></el-table-column>
          <el-table-column prop="username" label="用户名"></el-table-column>
          <el-table-column prop="password" label="用户密码"></el-table-column>
          <el-table-column prop="role" label="用户身份">
            <template slot-scope="scope">
              <span>{{ getRoleLabel(scope.row.role) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="teamId" label="用户所属团队Id"></el-table-column>
          <el-table-column prop="teamName" label="用户所属团队名称"></el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" plain @click="del(scope.row.userId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="users.length"
          layout="sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 20]"
          style="margin-top: 20px;"
      ></el-pagination>


        <el-dialog :title="editMode ? '编辑用户' : '新增用户'" :visible.sync="dialogVisible" @close="handleDialogClose">
            <el-form :model="newUser" label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="newUser.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户密码">
                    <el-input v-model="newUser.password" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="用户角色">
                    <el-select v-model="newUser.role" placeholder="请选择角色" style="width: 100%;">
                        <el-option label="队长" value="leader"></el-option>
                        <el-option label="队员" value="member"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属团队">
                    <el-select v-model="newUser.teamId" placeholder="请选择团队" style="width: 100%;">
                        <el-option v-for="team in teamList" :key="team.teamId" :label="team.teamName"
                            :value="team.teamId"></el-option>
                    </el-select>
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
import axios from 'axios';

export default {
    data() {
        return {
            searchQuery: null, // 搜索关键词
            users: [

            ],
            teamList: [], // 存储团队数据列表
            selectedTeamId: null, // 选中的团队数据
            dialogVisible: false,
            newUser: {
                userId: null,
                username: "",
                password: null,
                role: null,
                teamId: null,

            },
            editMode: false,
            paginatedUsers: [],
           currentPage: 1,
           pageSize: 10,
        };
    },
    created() {
        this.load(); // 页面加载时获取数据
    },
  watch: {
    users() {
      this.paginateUsers();
    },
  },
  mounted() {
    this.load(); // 初始加载
  },

    methods: {
      paginateUsers() {
        const start = (this.currentPage - 1) * this.pageSize;
        const end = start + this.pageSize;
        this.paginatedUsers = this.users.slice(start, end);
      },
      handleCurrentChange(page) {
        this.currentPage = page;
        this.paginateUsers();
      },
      handleSizeChange(size) {
        this.pageSize = size;
        this.paginateUsers();
      },



        getRoleLabel(role) {
            switch (role) {
                case "leader": return "队长";
                case "member": return "队员";
            }
        },

        handleDialogClose() {
            this.dialogVisible = false;
        },

        async load() {
            try {
                // 并行请求用户数据和团队数据
                const [userResponse, teamResponse] = await Promise.all([
                    axios.get('http://localhost:8080/user/all'),
                    axios.get('http://localhost:8080/team/list'),
                ]);

                // 校验响应是否成功
                if (userResponse.status === 200 && teamResponse.status === 200) {
                    const users = userResponse.data.data; // 假设用户数据在 data.data
                    const teams = teamResponse.data; // 假设团队数据在 data.data
                    this.teamList = teams; // 存储团队数据
                    // console.log("用户数据:", users);
                    // console.log("团队数据:", teams);

                    // 用团队数据创建一个映射表（teamId -> teamName）
                    const teamMap = {};
                    teams.forEach((team) => {
                        teamMap[team.teamId] = team.teamName;
                    });

                    // 为每个用户添加对应的团队名称
                    const enrichedUsers = users.map((user) => {
                        return {
                            ...user,
                            teamName: teamMap[user.teamId] || "未分配团队", // 如果没有匹配团队，显示“未分配团队”
                        };
                    });

                    // 更新表格数据
                    this.users = enrichedUsers;
                } else {
                    console.error("服务器返回了不符合预期的响应", userResponse, teamResponse);
                }
            } catch (error) {
                // 错误处理
                if (error.response) {
                    console.error("服务器错误:", error.response.data);
                } else if (error.request) {
                    console.error("没有收到响应:", error.request);
                } else {
                    console.error("请求错误:", error.message);
                }
            }
        }

        ,
        async search() {

            // 如果没有输入查询条件，提醒用户
            if (!this.searchQuery) {
                this.$message.error('请输入用户名字');
                return;
            }

            try {
                // 使用输入的团队 ID 调用后端接口
                const response = await axios.get(`http://localhost:8080/user/${this.searchQuery}`);

                // 成功获取数据后，更新表格数据
                if (response.status === 200 && response.data) {
                    console.log(response.data);
                    this.users = [response.data.data];  // 假设返回的是一个团队对象
                } else if (!response.data) {
                    this.$message.error("没有这位用户");
                }
            } catch (error) {
                // 处理查询失败的情况
                this.$message.error("查询失败");
                console.error("查询失败:", error);
            }
        },
        reset() {
            // 重置搜索条件并重新加载数据
            this.searchQuery = '';
            this.load();
        },
        handleAdd() {
            // 处理新增操作
            console.log("新增用户");
            this.newUser = {
                userId: null,
                username: "",
                password: null,
                role: null,
                teamId: null,
            };
            this.editMode = false;  // 设置为新增模式
            this.dialogVisible = true; // 显示对话框
        },
        async del(userId) {
            try {
                console.log(userId);
                const response = await axios.delete(`http://localhost:8080/user/admindelete/${userId}`);
                if (response.status === 200) {
                    this.$message.success(response.data.msg);
                } else {
                    this.$message.error(response.data.msg);
                }
                this.load(); // 删除后重新加载数据
            } catch (error) {
                console.error("删除失败:", error);
            }
        },
        handleEdit(row) {
            console.log(row);
            this.newUser = { ...row }
            this.editMode = true;
            this.dialogVisible = true; // 显示对话框
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        async saveTeam() {
            try {
                if (this.editMode) {
                    console.log("---------:", this.editMode)
                    console.log("新用户:", this.newUser)
                    await axios.put(`http://localhost:8080/user/adminupdate/${this.newUser.userId}`, this.newUser);
                    this.$message.success("更新成功");
                } else {
                    console.log("---------: 新增", this.editMode)
                    console.log("新用户:", this.newUser)
                    await axios.post('http://localhost:8080/user/admincreate', this.newUser);
                    this.$message.success("新增成功");
                }
                this.dialogVisible = false;
                this.load(); // 保存后重新加载数据


            } catch (error) {
                this.$message.error(this.editMode ? "更新失败" : "新增失败");
                console.error(this.editMode ? "更新失败" : "新增失败", error);
            }
        }

    },
};
</script>

<style scoped>
.search,
.operation {
    margin-bottom: 15px;
}


.search,
.operation {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.table {
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 10px;
}
</style>