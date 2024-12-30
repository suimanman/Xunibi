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
            <el-table :data="filteredUsers" border>
                <el-table-column prop="adminId" label="用户ID" sortable></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="password" label="用户密码"></el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" plain @click="del(scope.row.adminId)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog :title="editMode ? '编辑用户' : '新增用户'" :visible.sync="dialogVisible">
            <el-form :model="newUser" label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="newUser.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户密码">
                    <el-input v-model="newUser.password" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="saveUser">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            users: [], // 用户数据
            searchQuery: "", // 搜索关键词
            filteredUsers: [], // 筛选后的用户
            dialogVisible: false, // 控制弹窗显示
            editMode: false, // 判断是新增还是编辑
            newUser: {
                adminId: null,
                username: "",
                password: ""
            }
        };
    },
    mounted() {
        this.fetchUsers()

    },
    methods: {
        // 获取用户列表
        fetchUsers() {
            axios
                .get("http://localhost:8080/admins/list", { withCredentials: true }, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                })
                .then((response) => {
                    // console.log("response : ", response.data);
                    this.users = response.data;
                    this.filteredUsers = this.users;
                })
                .catch((error) => {
                    console.error("获取用户列表失败:", error);
                });
        },
        // 查询用户
        search() {
            this.filteredUsers = this.users.filter((user) =>
                user.username.includes(this.searchQuery)
            );
        },
        searchByName() {
            axios
                .get(`http://localhost:8080/admins/findbyname?username=${this.searchQuery}`,
                    {
                        withCredentials: true,
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded",
                        },
                    })
                .then((response) => {
                    this.filteredUsers = [response.data];
                    console.log("response : ", response.data);
                })
        },

        // 重置查询
        reset() {
            this.searchQuery = "";
            this.filteredUsers = this.users;
        },
        // 新增用户
        handleAdd() {
            this.newUser = { id: null, username: "", password: "" };
            this.editMode = false;
            this.dialogVisible = true;
        },
        // 编辑用户
        handleEdit(user) {
            this.newUser = { ...user }; // 深拷贝用户数据
            this.editMode = true;
            this.dialogVisible = true;
        },
        // 删除用户
        del(userId) {
            axios
                .delete(`http://localhost:8080/admins/delete/${userId}`, { withCredentials: true },
                )
                .then(() => {
                    this.$message.success("删除成功");
                    this.fetchUsers();
                })
                .catch((error) => {
                    console.error("删除失败:", error);
                });
        },
        // 保存用户（新增或编辑）
        saveUser() {
            if (this.editMode) {
                // 编辑模式
                axios
                    .put(
                        `http://localhost:8080/admins/update/${this.newUser.adminId}`,
                        {
                            adminId: this.newUser.adminId,
                            username: this.newUser.username,
                            password: this.newUser.password
                        },
                        {
                            withCredentials: true,
                            headers: {
                                "Content-Type": "application/json", // 这里最好使用 "application/json"
                            },


                        },
                    )
                    .then(() => {
                        this.$message.success("编辑成功");
                        this.dialogVisible = false;
                        this.fetchUsers();
                    })
                    .catch((error) => {
                        console.error("编辑失败:", error);
                    });
            } else {
                // 新增模式
                axios
                    .post(
                        "http://localhost:8080/admins/create",
                        {
                            username: this.newUser.username,
                            password: this.newUser.password
                        },
                        {
                            withCredentials: true,
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded",
                            },
                        },

                    )
                    .then(() => {
                        this.$message.success("新增成功");
                        this.dialogVisible = false;
                        this.fetchUsers();
                    })
                    .catch((error) => {
                        console.error("新增失败:", error);
                    });
            }
        }
    },
    mounted() {
        this.fetchUsers();
    }
};
</script>

<style scoped>
.search {
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
