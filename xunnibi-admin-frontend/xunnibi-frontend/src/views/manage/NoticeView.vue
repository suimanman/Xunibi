<template>
    <div>
        <!-- 搜索框 -->
        <div class="search">
            <el-input v-model="searchQuery" placeholder="请输入id查询" style="width: 200px" />
            <el-button type="info" plain style="margin-left: 10px" @click="search" icon="el-icon-zoom-in">查询</el-button>
            <el-button type="warning" plain style="margin-left: 10px" @click="reset"
                icon="el-icon-refresh-left">重置</el-button>
            <el-button type="primary" plain @click="openDialog('add')">新增</el-button>
        </div>

        <!-- 公告表格 -->
        <div class="table">
            <el-table :data="filteredNoticeData" border>
                <el-table-column prop="id" label="Id" sortable width="150" />
                <el-table-column prop="content" label="公告内容" />
                <el-table-column prop="createdAt" label="公告发布时间" :formatter="formatDate" width="180" />
                <el-table-column prop="display" label="是否显示" :formatter="formatDisplay" width="120" />
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" plain @click="openDialog('edit', scope.row)">
                            编辑
                        </el-button>
                        <el-button size="mini" type="danger" plain @click="del(scope.row.id)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 新增/编辑弹窗 -->
        <el-dialog :title="dialogTitle" :visible="dialogVisible" width="500px">
            <el-form :model="form" :rules="formRules" ref="formRef" label-width="100px">
                <el-form-item label="公告内容" prop="content">
                    <el-input v-model="form.content" placeholder="请输入公告内容" />
                </el-form-item>
                <el-form-item label="是否显示" prop="display">
                    <el-switch v-model="form.display" :active-text="'显示'" :inactive-text="'隐藏'" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:8080", // 后端服务地址
    timeout: 10000, // 请求超时时间
});

export default {
    data() {
        return {
            searchQuery: "", // 搜索内容
            noticeData: [], // 公告数据
            filteredNoticeData: [], // 筛选后的公告数据
            dialogVisible: false, // 弹窗是否可见
            dialogTitle: "", // 弹窗标题
            form: {
                id: null,
                content: "",
                display: false, // 默认显示
            },
            formRules: {
                content: [{ required: true, message: "请输入公告内容", trigger: "blur" }],
                display: [{ required: true, message: "请选择是否显示", trigger: "change" }],
            },
        };
    },
    methods: {
        // 查询公告（在前端筛选）
        search() {
            if (!this.searchQuery) {
                this.filteredNoticeData = this.noticeData;
                this.$message.info("请输入查询条件！");
                return;
            }
            this.filteredNoticeData = this.noticeData.filter((item) =>
                item.id.toString().includes(this.searchQuery)
            );
            if (this.filteredNoticeData.length > 0) {
                this.$message.success("筛选完成！");
            } else {
                this.$message.warning("未找到匹配的公告！");
            }
        },
        // 重置查询条件
        reset() {
            this.searchQuery = "";
            this.filteredNoticeData = this.noticeData; // 恢复为完整数据
            this.$message.info("查询条件已重置！");
        },
        // 打开新增/编辑弹窗
        openDialog(type, row = {}) {
            this.dialogTitle = type === "add" ? "新增公告" : "编辑公告";
            this.dialogVisible = true;
            if (type === "add") {
                // 清空表单数据
                this.form = { id: null, content: "", display: null };
            } else {
                this.form = {
                    ...row,
                    display: row.display === true || row.display === "true",
                };
            }
        },
        // 保存公告
        save() {
            this.$refs.formRef.validate((valid) => {
                if (!valid) return;

                const apiCall = this.form.id
                    ? http.put(`/notices/${this.form.id}`, this.form) // 编辑
                    : http.post("/notices", this.form); // 新增

                apiCall
                    .then(() => {
                        this.$message.success(this.form.id ? "编辑成功！" : "新增成功！");
                        this.dialogVisible = false;
                        this.fetchData(); // 刷新数据
                    })
                    .catch((error) => {
                        console.error("Failed to save notice:", error);
                        this.$message.error(this.form.id ? "编辑失败！" : "新增失败！");
                    });
            });
        },
        // 删除公告
        del(id) {
            this.$confirm("确认删除此公告吗？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    http.delete(`/notices/${id}`)
                        .then(() => {
                            this.$message.success("删除成功！");
                            this.fetchData(); // 重新加载数据
                        })
                        .catch((error) => {
                            console.error("Failed to delete:", error);
                            this.$message.error("删除失败！");
                        });
                })
                .catch(() => {
                    this.$message.info("已取消删除");
                });
        },
        // 格式化是否显示
        formatDisplay(row) {
            return row.display === true ? "是" : "否";
        },
        // 格式化日期
        formatDate(row) {
            const date = new Date(row.createdAt);
            return date.toLocaleString(); // 本地化时间
        },
        // 获取公告数据
        fetchData() {
            http.get("/notices")
                .then((response) => {
                    this.noticeData = response.data.data || [];
                    this.filteredNoticeData = this.noticeData; // 初始化时显示全部数据
                })
                .catch((error) => {
                    console.error("Failed to fetch data:", error);
                    this.$message.error("加载公告失败！");
                });
        },
    },
    mounted() {
        // 初始化加载公告数据
        this.fetchData();
    },
};
</script>