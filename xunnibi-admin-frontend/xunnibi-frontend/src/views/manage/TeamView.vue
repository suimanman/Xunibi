<template>
    <div class="container">
        <div class="search">
            <el-input v-model="searchQuery" placeholder="请输入团队id查询" style="width: 200px"></el-input>
            <el-button type="info" plain style="margin-left: 10px" @click="search" icon="el-icon-zoom-in">查询</el-button>
            <el-button type="warning" plain style="margin-left: 10px" @click="reset"
                icon="el-icon-refresh-left">重置</el-button>
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </div>
        <div class="table">
            <el-table :data="teams" @selection-change="handleSelectionChange" border>
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

        <el-dialog :title="editMode ? '编辑团队' : '新增团队'" :visible="dialogVisible" @close="handleDialogClose">
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
import axios from 'axios';

export default {
    data() {
        return {
            searchQuery: null, // 搜索关键词
            teams: [

            ], // 团队数据列表
            selectedTeamId: null, // 选中的团队数据
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
    methods: {
        handleDialogClose() {
            this.dialogVisible = false;
        },
        async load() {
            try {
                const response = await axios.get('http://localhost:8080/team/list');
                if (response.status === 200 && response.data) {
                    console.log("团队列表数据:", response.data);
                    this.teams = response.data; // 假设返回的数据包含团队列表
                    console.log("团队列表数据teams:", this.teams);
                } else {
                    console.error("服务器返回了不符合预期的响应", response);
                }
            } catch (error) {
                if (error.response) {
                    // 服务器响应了错误状态码
                    console.error("服务器错误:", error.response.data);
                } else if (error.request) {
                    // 请求没有收到响应
                    console.error("没有收到响应:", error.request);
                } else {
                    // 其他类型的错误（如请求配置错误）
                    console.error("请求错误:", error.message);
                }
            }
        }
        ,
        async search() {

            // 如果没有输入查询条件，提醒用户
            if (!this.searchQuery) {
                this.$message.error('请输入团队ID');
                return;
            }

            try {
                // 使用输入的团队 ID 调用后端接口
                const response = await axios.get(`http://localhost:8080/team/${this.searchQuery}`);

                // 成功获取数据后，更新表格数据
                if (response.status === 200 && response.data) {
                    console.log(response.data);
                    this.teams = [response.data];  // 假设返回的是一个团队对象
                } else if (!response.data) {
                    this.$message.error("没有这支团队");
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
            console.log("新增团队");
            this.newTeam = {
                teamId: null,
                teamName: "",
                virtualCoins: 0,
                creationDate: null,
            };
            this.editMode = false;  // 设置为新增模式
            this.dialogVisible = true; // 显示对话框
        },
        delBatch() {
            // 批量删除选中的团队
            console.log("批量删除:", this.selectedTeams);
            this.selectedTeams.forEach(team => {
                this.del(team.teamId);
            });
        },
        async del(teamId) {
            try {
                console.log(teamId);
                const response = await axios.delete(`http://localhost:8080/team/delete/${teamId}`);
                console.log("删除信息:", response);
                if (response.data.code === 200) {
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
            this.newTeam = { ...row }
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
                    await axios.put(`http://localhost:8080/team/update/${this.newTeam.teamId}`, this.newTeam);
                    this.$message.success("更新成功");
                } else {
                    console.log("---------: 新增", this.editMode)
                    await axios.post('http://localhost:8080/team/create', this.newTeam);
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
.container {
    padding: 20px;
    background-color: #f9f9f9;
}

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