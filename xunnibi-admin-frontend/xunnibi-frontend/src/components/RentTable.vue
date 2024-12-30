<template>
    <div class="table-container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入物品名称" style="width: 250px"></el-input>
            <el-button type="info" plain @click="search">查询</el-button>
            <el-button type="warning" plain @click="reset">重置</el-button>
            <el-button type="primary" plain @click="addItem">新增</el-button>
        </div>
        <div class="table-wrapper">
            <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
                <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label"
                    align="center" :width="col.width">
                    <template v-if="col.slot" #default="scope">
                        <slot :name="col.slot" :scope="scope"></slot>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template #default="scope">
                        <el-button size="mini" type="primary" plain @click="editItem(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" plain @click="deleteItem(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        columns: {
            type: Array,
            required: true,
        },
        data: {
            type: Array,
            default: () => [],
        },
    },
    data() {
        return {
            searchQuery: "",
            tableData: this.data,
        };
    },
    watch: {
        data(newData) {
            this.tableData = newData;
        },
    },
    methods: {
        search() {
            this.$emit("search", this.searchQuery);
        },
        reset() {
            this.searchQuery = "";
            this.clearFilter();
            this.$emit("reset");
        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        addItem() {
            this.$emit("add");
        },
        editItem(row) {
            this.$emit("edit", row);
        },
        deleteItem(row) {
            this.$emit("delete", row);
        },
    },
};
</script>

<style scoped>
.table-container {
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
    width: 100%;
    /* 或者一个固定值，比如 800px */
    height: calc(100% - 50px);
    /* 如果需要动态高度 */
    overflow: auto;
    /* 确保内容溢出时不会影响布局 */
}
</style>
