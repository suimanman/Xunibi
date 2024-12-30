<template>
    <div>
        <el-tabs type="border-card" v-model="activeTab">
            <el-tab-pane label="工位" name="工位">
                <RentTable :columns="columns" :data="tableData" @search="searchStationTable" @reset="resetStationTable"
                    @add="handleAddStation" @edit="handleEditStation" @delete="deleteStationRow">
                    <template #imageSlot="{ scope }">
                        <img :src="scope.row.imageUrl" alt="物品图片" style="width: 80px; height: auto;" />
                    </template>
                    <template #isAvailableSlot="{ scope }">
                        <span :style="{ color: scope.row.isAvailable ? 'green' : 'red' }">
                            {{ scope.row.isAvailable ? '可用' : '不可用' }}
                        </span>
                    </template>

                </RentTable>
            </el-tab-pane>
            <el-tab-pane label="相机" name="相机">
                <RentTable :columns="columns" :data="tableData" @search="searchCameraTable" @reset="resetCameraTable"
                    @add="handleAddCamera" @edit="handleEditCamera" @delete="deleteCameraRow">

                    <template #imageSlot="{ scope }">
                        <img :src="scope.row.imageUrl" alt="物品图片" style="width: 80px; height: auto;" />
                    </template>
                    <template #isAvailableSlot="{ scope }">
                        <span :style="{ color: scope.row.isAvailable ? 'green' : 'red' }">
                            {{ scope.row.isAvailable ? '可用' : '不可用' }}
                        </span>
                    </template>
                </RentTable>
            </el-tab-pane>
            <el-tab-pane label="其他设备" name="其他设备">
                <RentTable :columns="columns" :data="tableData" @search="searchOtherTable" @reset="resetOtherTable"
                    @add="handleAddOther" @edit="handleEditOther" @delete="deleteOtherRow">
                    <template #imageSlot="{ scope }">
                        <img :src="scope.row.imageUrl" alt="物品图片" style="width: 80px; height: auto;" />
                    </template>
                    <template #isAvailableSlot="{ scope }">
                        <span :style="{ color: scope.row.isAvailable ? 'green' : 'red' }">
                            {{ scope.row.isAvailable ? '可用' : '不可用' }}
                        </span>
                    </template>
                </RentTable>
            </el-tab-pane>
        </el-tabs>



        <!-- 弹出表单对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle">
            <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
                <el-form-item label="物品名称" prop="type">
                    <el-input v-model="form.type"></el-input>
                </el-form-item>


                <el-form-item label="物品图片" prop="imageFile">
                    <el-upload class="upload-demo" action="http://localhost:8080/files/upload"
                        :on-success="handleUploadSuccess" :before-upload="beforeUpload" :file-list="fileList"
                        :show-file-list="false">
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>




                <el-form-item label="虚拟币价格" prop="coinConsumption">
                    <el-input type="number" v-model="form.coinConsumption"></el-input>
                </el-form-item>
                <el-form-item label="是否可用" prop="isAvailable">
                    <el-switch v-model="isAvailableBoolean"></el-switch>
                </el-form-item>
                <el-form-item label="租用队伍" prop="rentedTeamId">
                    <el-select v-model="form.rentedTeamId" placeholder="请选择队伍">
                        <el-option v-for="team in teams" :key="team.teamId" :label="team.teamName"
                            :value="team.teamId"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import RentTable from "@/components/RentTable.vue";
import axios from "axios";

export default {
    components: { RentTable },
    data() {
        return {
            fileList: [],  // 存储已上传的文件列表
            teams: [], // 团队数据列表
            teamMap: {}, // 映射 { teamId: teamName }
            activeTab: "工位", // 默认加载的 Tab
            tableData: [], // 当前 Tab 的表格数据
            dialogVisible: false,
            dialogTitle: "",
            form: {
                id: null,
                type: "",
                coinConsumption: null,
                isAvailable: 0,
                rentedTeamId: null,
                imageUrl: "",  // 新增字段来存储图片的 URL
            },
            rules: {
                type: [{ required: true, message: "请输入物品名称", trigger: "blur" }],
                coinConsumption: [{ required: true, message: "请输入价格", trigger: "blur" }],
            },
            columns: [
                { prop: "id", label: "物品id", width: 80 },
                { prop: "imageUrl", label: "图片", slot: "imageSlot", width: 150 }, // 新增图片列
                { prop: "type", label: "物品名称", width: 150 },
                { prop: "coinConsumption", label: "虚拟币价格", width: 100 },
                { prop: "isAvailable", label: "是否可用", slot: "isAvailableSlot", width: 100 },
                { prop: "rentedTeamId", label: "租用队伍", width: 150 },
                { prop: "teamName", label: "租用队伍名称", width: 200 },
            ],
        };
    },
    computed: {
        // 将 isAvailable 转换为布尔值
        isAvailableBoolean: {
            get() {
                return this.form.isAvailable === 1;
            },
            set(value) {
                this.form.isAvailable = value ? 1 : 0;
            }
        }
    },
    methods: {
        handleUploadSuccess(response, file, fileList) {
            console.log("res:", response)
            if (response.code === 200) {  // 检查上传是否成功
                this.form.imageUrl = response.data;  // 更新表单中的图片 URL
                this.$message.success("图片上传成功！");
            } else {
                this.$message.error("图片上传失败，请重试！");
            }
        },
        beforeUpload(file) {
            const isImage = file.type.startsWith('image/');
            if (!isImage) {
                this.$message.error("只能上传图片文件！");
            }
            return isImage;
        },
        loadTeams() {
            axios
                .get("http://localhost:8080/team/list")
                .then((response) => {
                    this.teams = response.data;
                    this.teamMap = this.teams.reduce((map, team) => {
                        map[team.teamId] = team.teamName;
                        return map;
                    }, {});
                    // 更新表格数据，确保 teamName 显示正确
                    this.loadTableData();
                    console.log("teams:", this.teams);
                })
                .catch((error) => {
                    console.error("加载团队列表失败：", error);
                    this.$message.error("加载团队列表失败，请稍后重试");
                });
        },
        // 通用加载数据方法，根据 activeTab 加载对应数据
        loadTableData() {
            let endpoint = this.getEndpoint();
            axios
                .get(endpoint, {
                    // withCredentials: true,  // 如果需要携带 cookies 或身份验证信息
                })
                .then((response) => {

                    this.data = response.data.data;
                    this.tableData = this.data.map((item) => ({
                        ...item,
                        teamName: this.teamMap[item.rentedTeamId] || "未分配",
                        imageUrl: item.imageUrl && typeof item.imageUrl === 'string' && item.imageUrl.startsWith('http')
                            ? item.imageUrl
                            : `http://localhost:8080${item.imageUrl || ''}`, // 处理空值
                    }));

                    console.log("tabledata:", this.tableData);

                })
                .catch((error) => {
                    console.error(`加载${this.activeTab}数据失败：`, error);
                    this.$message.error(`加载${this.activeTab}数据失败，请稍后重试`);
                });
        },

        // 根据 activeTab 获取 API 端点
        getEndpoint() {
            if (this.activeTab === "工位") return "http://localhost:8080/items/workstation/all";
            if (this.activeTab === "相机") return "http://localhost:8080/items/camera/all";
            if (this.activeTab === "其他设备") return "http://localhost:8080/items/equipment/all";
            return "";
        },
        // 搜索功能
        searchStationTable(query) {
            axios
                .get("http://localhost:8080/items/workstation/findbyname", { params: { name: query } })
                .then((response) => {
                    this.tableData = response.data.data;
                })
                .catch((error) => {
                    console.error("搜索工位数据失败：", error);
                    this.$message.error("搜索工位数据失败，请稍后重试");
                });
        },
        searchCameraTable(query) {
            axios
                .get("http://localhost:8080/items/camera/findbyname", { params: { name: query } })
                .then((response) => {
                    this.tableData = response.data.data;
                })
                .catch((error) => {
                    console.error("搜索相机数据失败：", error);
                    this.$message.error("搜索相机数据失败，请稍后重试");
                });
        },
        searchOtherTable(query) {
            axios
                .get("http://localhost:8080/items/equipment/findbyname", { params: { name: query } })
                .then((response) => {
                    this.tableData = response.data.data;
                })
                .catch((error) => {
                    console.error("搜索其他设备数据失败：", error);
                    this.$message.error("搜索其他设备数据失败，请稍后重试");
                });
        },
        // 重置功能
        resetStationTable() {
            this.loadTableData();
        },
        resetCameraTable() {
            this.loadTableData();
        },
        resetOtherTable() {
            this.loadTableData();
        },

        // 新增工位
        handleAddStation() {
            this.dialogTitle = "新增工位";  // 设置对话框标题
            this.form = { id: null, type: "", coinConsumption: null, isAvailable: 0 }; // 清空表单
            this.dialogVisible = true;  // 显示弹框
        },
        // 新增相机
        handleAddCamera() {
            this.dialogTitle = "新增相机";  // 设置对话框标题
            this.form = { id: null, type: "", coinConsumption: null, isAvailable: 0 }; // 清空表单
            this.dialogVisible = true;  // 显示弹框
        },
        // 新增其他设备
        handleAddOther() {
            this.dialogTitle = "新增其他设备";  // 设置对话框标题
            this.form = { id: null, type: "", coinConsumption: null, isAvailable: 0 }; // 清空表单
            this.dialogVisible = true;  // 显示弹框
        },
        // 提交表单数据
        handleSubmit() {
            this.$refs.formRef.validate((valid) => {
                if (!valid) return;
                const { addEndpoint, editEndpoint } = this.getApiEndpoints();
                const method = this.form.id ? "put" : "post";  // 如果有 ID 说明是编辑，反之为新增
                const endpoint = this.form.id ? editEndpoint : addEndpoint;

                const formData = {
                    ...this.form,
                    isAvailable: this.form.isAvailable ? 1 : 0
                };
                console.log("thisform:", formData);
                axios[method](endpoint, formData)
                    .then(() => {
                        this.$message.success(`${this.dialogTitle}成功`);
                        this.dialogVisible = false;
                        this.loadTableData(); // 刷新数据
                    })
                    .catch((error) => {
                        console.error(`${this.dialogTitle}失败：`, error);
                        this.$message.error(`${this.dialogTitle}失败，请稍后重试`);
                    });
            });
        },

        // 获取不同 Tab 对应的 API 地址
        getApiEndpoints() {
            if (this.activeTab === "工位") {
                return {
                    addEndpoint: "http://localhost:8080/items/workstation/add",
                    editEndpoint: `http://localhost:8080/items/workstation/update/${this.form.id}`,



                };
            }
            if (this.activeTab === "相机") {
                return {
                    addEndpoint: "http://localhost:8080/items/camera/add",
                    editEndpoint: `http://localhost:8080/items/camera/update/${this.form.id}`,
                };
            }
            if (this.activeTab === "其他设备") {
                return {
                    addEndpoint: "http://localhost:8080/items/equipment/add",
                    editEndpoint: `http://localhost:8080/items/workstation/update/${this.form.id}`

                };
            }
            return {};
        },


        // 编辑功能
        handleEditStation(row) {
            this.dialogTitle = `编辑工位: ${row.type}`;  // 设置对话框标题
            this.form = { ...row };  // 将表单数据填充为所选行数据
            this.dialogVisible = true;  // 显示弹框
        },
        handleEditCamera(row) {
            this.dialogTitle = `编辑相机: ${row.type}`;  // 设置对话框标题
            this.form = { ...row };  // 将表单数据填充为所选行数据
            this.dialogVisible = true;  // 显示弹框
        },
        handleEditOther(row) {
            this.dialogTitle = `编辑设备: ${row.type}`;  // 设置对话框标题
            this.form = { ...row };  // 将表单数据填充为所选行数据
            this.dialogVisible = true;  // 显示弹框
        },
        // 删除功能
        deleteStationRow(row) {
            this.confirmDelete(`http://localhost:8080/items/workstation/delete/${row.id}`, "工位", row);
        },
        deleteCameraRow(row) {
            this.confirmDelete(`http://localhost:8080/items/camera/delete/${row.id}`, "相机", row);
        },
        deleteOtherRow(row) {
            this.confirmDelete(`http://localhost:8080/items/equipment/delete/${row.id}`, "其他设备", row);
        },
        confirmDelete(endpoint, label, row) {
            this.$confirm(
                `确定删除${label} ${row.type} 吗？`,
                "提示",
                {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }
            )
                .then(() => axios.delete(endpoint))
                .then(() => {
                    this.$message.success("删除成功");
                    this.loadTableData();
                })
                .catch((error) => {
                    if (error !== "cancel") {
                        console.error(`删除${label}失败：`, error);
                        this.$message.error(`删除${label}失败，请稍后重试`);
                    }
                });
        },
    },
    watch: {
        // Tab 切换时加载对应数据
        activeTab() {
            this.loadTableData();
        },
    },
    mounted() {
        this.loadTableData();
        this.loadTeams(); // 默认加载工位数据
    },

};
</script>

<style scoped>
/* 根据需要添加样式 */
</style>
