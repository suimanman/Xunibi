<template>
    <div class="container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入团队Id" style="width: 250px"></el-input>
            <el-button type="info" plain @click="search" icon="el-icon-zoom-in">查询</el-button>
            <el-button type="warning" plain @click="reset" icon="el-icon-refresh-left">重置</el-button>
        </div>

        <div class="table-wrapper">
            <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
                <el-table-column prop="achievementId" label="id" align="center" width="100px">
                </el-table-column>
                <el-table-column prop="material" label="审核材料" align="left">
                </el-table-column>
                <el-table-column prop="achievementType" label="审核材料类型" align="center" width="150px">
                </el-table-column>
                <el-table-column prop="teamId" label="团队Id" align="center" width="100px">
                </el-table-column>
                <el-table-column prop="teamName" label="团队名称" align="center" width="100px"></el-table-column>
                <el-table-column prop="status" label="审核状态" align="center" width="100px">
                </el-table-column>
                <el-table-column label="操作" align="center" width="300px">
                    <template v-slot="scope">
                        <el-button size="mini" type="primary" plain :disabled="scope.row.reviewProcess === '已通过'"
                            @click="openDialog(scope.row)">审核通过</el-button>
                        <el-button size="mini" type="danger" plain @click="handleNoPass(scope.row)">审核不通过</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 审核通过弹窗 -->
        <el-dialog title="增加虚拟币" :visible.sync="dialogVisible" width="400px">
            <div>
                <el-form>
                    <el-form-item label="增加虚拟币数量">
                        <el-input-number v-model="coinAmount" label="虚拟币" />
                    </el-form-item>
                </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handlePass">确认</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            teamList: [], // 团队数据列表
            searchQuery: null,
            tableData: [], // 假数据容器
            dialogVisible: false, // 控制弹窗显示
            selectedRow: null, // 当前操作的行数据
            coinAmount: 0, // 增加的虚拟币数量
        };
    },
    created() {
        // // 生成假数据
        // this.tableData = Array.from({ length: 10 }).map((_, index) => ({
        //     teamId: `T${1000 + index}`, // 假团队ID
        //     teamName: `团队${index + 1}`, // 假团队名称
        //     material: `材料${index + 1}`, // 假审核材料

    },
    mounted() {
        this.getAchievementList();
    },
    methods: {
        search() {
            // 检查输入是否合法
            if (!this.searchQuery) {
                this.$message.warning("请输入团队Id后再查询");
                return;
            }

            // 发起请求
            axios
                .get(`http://localhost:8080/gain/achievementListById/${this.searchQuery}`)
                .then((response) => {
                    console.log("获取指定团队的待审核列表信息:", response.data);
                    if (response.data.code === 200) {
                        // 更新表格数据
                        this.tableData = response.data.data.map((item) => ({
                            ...item,
                            material: item.description, // 设置审核材料字段
                        }));
                        if (this.tableData.length === 0) {
                            this.$message.info("未找到符合条件的数据");
                        }
                    } else {
                        this.$message.error(response.data.msg || "查询失败");
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error("查询失败");
                });
        },

        // getAchievementList() {
        //     axios
        //         .get("http://localhost:8080/gain/achievementList")
        //         .then((response) => {
        //             console.log("获取待审核列表信息:", response.data);
        //             if (response.data.code === 200) {

        //                 this.tableData = response.data.data.map((item) => ({
        //                     ...item,
        //                     material: item.description,
        //                 })); // 设置待审核成就列表
        //             } else {
        //                 this.$message.error(response.data.msg || "获取待审核列表失败");
        //             }
        //         })
        //         .catch((error) => {
        //             console.error(error);
        //             this.$message.error("获取待审核列表失败");
        //         });
        // },

        async getAchievementList() {
            try {
                const [achievementListResponse, teamListResponse] = await Promise.all([
                    axios.get("http://localhost:8080/gain/achievementList"),
                    axios.get("http://localhost:8080/team/list"),
                ]);
                if (achievementListResponse.data.code === 200 && teamListResponse.status === 200) {

                    this.tableData = achievementListResponse.data.data.map((item) => {
                        const team = teamListResponse.data.find((t) => t.teamId === item.teamId);
                        return {
                            ...item,
                            material: item.description,
                            teamName: team ? team.teamName : "未知",
                        }

                    })

                    console.log("tableData:", this.tableData)
                }
            } catch (error) {
                console.error("获取待审核列表失败:", error);
                this.$message.error("获取待审核列表失败，请稍后重试");
            }
        },

        openDialog(row) {
            this.selectedRow = row;
            this.coinAmount = 0;
            this.dialogVisible = true;
        },
        handlePass() {
            if (!this.selectedRow) return;
            axios
                .post("http://localhost:8080/gain/reward/achievement", {
                    achievementId: this.selectedRow.achievementId,
                    teamId: this.selectedRow.teamId,
                    coinAwarded: this.coinAmount,
                    status: "已通过",
                    achievementType: this.selectedRow.achievementType,
                })
                .then((response) => {
                    if (response.data.code === 200) {
                        this.$message.success("审核通过成功");
                        this.dialogVisible = false;
                        this.selectedRow.status = "已通过";
                        this.coinAmount = 0;
                    } else {
                        this.$message.error(response.data.msg || "审核失败");
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error("审核失败");
                });
        },
        handleNoPass(row) {
            axios
                .post("http://localhost:8080/gain/reward/achievement", {
                    achievementId: row.achievementId,
                    teamId: row.teamId,
                    status: "未通过",
                    coinAwarded: 0,
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
                    console.error(error);
                    this.$message.error("操作失败");
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
