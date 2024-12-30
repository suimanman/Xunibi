<template>
    <div class="container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入团队名称" style="width: 250px"></el-input>
            <el-button type="info" plain @click="search" icon="el-icon-zoom-in">查询</el-button>
            <el-button type="warning" plain @click="reset" icon="el-icon-refresh-left">重置</el-button>
        </div>

        <div class="table-wrapper">
            <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
                <el-table-column prop="transactionId" label="id" align="center">
                </el-table-column>
                <el-table-column prop="transactionDate" label="交易日期" align="center">
                </el-table-column>
                <el-table-column prop="transactionType" label="交易类型" align="center">
                </el-table-column>
                <el-table-column prop="teamName" label="团队名称" align="center"></el-table-column>
                <el-table-column prop="coinAmount" label="交易虚拟币数量" align="center"> </el-table-column>
                <el-table-column prop="description" label="交易内容" align="center"> </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            teamList: [], // 团队数据列表
            searchQuery: null,
            tableData: [],
        };
    },
    created() {
    },
    mounted() {
        this.getAchievementList();
    },
    methods: {
        // 查询功能
        search() {
            // 检查输入是否合法
            if (!this.searchQuery) {
                this.$message.warning("请输入团队名称查询");
                return;
            }

            // 根据团队名称过滤表格数据
            this.tableData = this.tableData.filter(item =>
                item.teamName.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        },

        // 重置查询
        reset() {
            this.searchQuery = null;
            this.getAchievementList();
        },

        // 获取待审核列表
        async getAchievementList() {
            try {
                const [achievementListResponse, teamListResponse] = await Promise.all([
                    axios.get("http://localhost:8080/records/virtualCoinRecords"),
                    axios.get("http://localhost:8080/team/list"),
                ]);
                if (achievementListResponse.data.code === 200 && teamListResponse.status === 200) {
                    this.tableData = achievementListResponse.data.data.map((item) => {
                        const team = teamListResponse.data.find((t) => t.teamId === item.teamId);
                        return {
                            ...item,
                            teamName: team ? team.teamName : "未知",
                        }
                    })
                }
            } catch (error) {
                console.error("获取待审核列表失败:", error);
                this.$message.error("获取待审核列表失败，请稍后重试");
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
