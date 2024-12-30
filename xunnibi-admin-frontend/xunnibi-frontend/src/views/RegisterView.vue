<template>
    <div class="register-container">
        <el-card class="register-card" shadow="always">
            <!-- 返回按钮，放在卡片的左上角 -->
            <i class="el-icon-caret-left return-login" @click="goToLogin">返回登陆</i>
            <h2 class="form-title">管理员注册</h2>
            <el-form :model="roleForm" status-icon :rules="rules" ref="roleForm" label-width="100px"
                class="demo-roleForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model.number="roleForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="pass">
                    <el-input type="password" v-model="roleForm.pass" placeholder="请输入密码" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input type="password" v-model="roleForm.checkPass" placeholder="请再次输入密码"
                        autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item class="form-buttons" style="margin-left: 0px;">
                    <el-button type="primary" size="large" round @click="submitForm('roleForm')">
                        提交
                    </el-button>
                    <el-button type="warning" size="large" plain round @click="resetForm('roleForm')">
                        重置
                    </el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
export default {
    data() {
        return {
            roleForm: {}
        }
    },
    methods: {
        goToLogin() {
            this.$router.push({ path: '/login' }); // 跳转到登录页面
        }
    }
}
</script>

<style scoped>
/* 居中布局 */
.register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: #f6f8fa;
}

/* 卡片样式 */
.register-card {
    position: relative;
    /* 让返回按钮可以相对于卡片定位 */
    width: 400px;
    padding: 20px;
    border-radius: 12px;
    background: #ffffff;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    text-align: center;
}

/* 返回按钮样式 */
.return-login {
    position: absolute;
    left: 20px;
    /* 距离左边20px */
    top: 20px;
    /* 距离顶部20px */
    cursor: pointer;
    color: #4a90e2;
    font-size: 16px;
}

/* 表单标题样式 */
.form-title {
    font-size: 24px;
    color: #333333;
    font-weight: bold;
    margin-bottom: 20px;
}

/* 输入框样式 */
.el-input__inner {
    height: 40px;
    border-radius: 6px;
    font-size: 14px;
}

/* 按钮样式 */
.form-buttons {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 20px;
}

.el-button {
    width: 100px;
    text-align: center;
}

/* 提交按钮 */
.el-button--primary {
    background: linear-gradient(90deg, #4a90e2, #8ec5fc);
    color: #ffffff;
    box-shadow: 0 4px 8px rgba(74, 144, 226, 0.3);
}

/* 重置按钮 */
.el-button--warning {
    border-color: #f5a623;
    color: #f5a623;
}
</style>
