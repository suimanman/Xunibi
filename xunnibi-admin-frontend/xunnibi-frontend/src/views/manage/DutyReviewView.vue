<template>
    <div class="container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入团队名称" style="width: 250px"></el-input>
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
                <el-table-column label="操作" align="center" width="300px">
                    <template v-slot="scope">
                        <el-button size="mini" type="primary" plain :disabled="scope.row.status === '已通过'"
                            @click="openDialog(scope.row)">
                            审核通过
                        </el-button>
                        <el-button size="mini" type="danger" plain @click="handleNoPass(scope.row)"
                            :disabled="scope.row.status === '已通过'">
                            审核不通过
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="增加虚拟币" :visible.sync="dialogVisible" width="400px">
            <div>
                <el-form>
                    <el-form-item label="增加虚拟币数量">
                        <el-input-number v-model="coinAmount" :min="0" label="虚拟币" />
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
            searchQuery: "", // 查询字段
            tableData: [], // 表格数据
            dialogVisible: false, // 弹窗显示控制
            selectedRow: null, // 当前选中的行
            coinAmount: 0
        };
    },
    mounted() {
        this.getAllDutyRecords(); // 初次加载获取所有记录
    },
    methods: {
        async getAllDutyRecords() {
            try {
                const [DutyListResponse, teamListResponse] = await Promise.all([
                    axios.get("http://localhost:8080/dutyrecords/reviewall"),
                    axios.get("http://localhost:8080/team/list"),
                ]);
                if (DutyListResponse.data.code === 200 && teamListResponse.status === 200) {
                    this.tableData = DutyListResponse.data.data.map((item) => {
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
        search() {
            if (!this.searchQuery) {
                this.$message.warning("请输入团队名称");
                return;
            }


            this.tableData = this.tableData.filter(
                item => item.teamName.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        },
        reset() {
            this.searchQuery = "";
            this.getAllDutyRecords();
        },
        openDialog(row) {
            this.selectedRow = row;
            this.dialogVisible = true;
        },
        handlePass() {
            if (!this.selectedRow) return;
            axios
                .post("http://localhost:8080/dutyrecords/reward", {
                    dutyId: this.selectedRow.dutyId,
                    teamId: this.selectedRow.teamId,
                    userId: this.selectedRow.userId,
                    coinAwarded: this.coinAmount,
                    status: "已通过",
                    dutyDate: this.selectedRow.dutyDate,
                    image: this.selectedRow.image
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
                .post("http://localhost:8080/dutyrecords/reward", {
                    dutyId: row.dutyId,
                    teamId: row.teamId,
                    userId: row.userId,
                    status: "未通过",
                    coinAwarded: 0,
                    dutyDate: row.dutyDate
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
