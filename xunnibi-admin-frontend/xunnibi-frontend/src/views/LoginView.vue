<style scoped>
.login-body {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f7fa;
}

.login-container {
    width: 400px;
}

.login-card {
    padding: 20px;
    border-radius: 10px;
}

.card-header {
    text-align: center;
    font-size: 20px;
    font-weight: bold;
    color: #333;
}

.login-form {
    margin-top: 20px;
}

.register-link {
    color: #409eff;
    cursor: pointer;
    text-decoration: underline;
}

.register-link:hover {
    color: #66b1ff;
}
</style>

<template>
    <div class="login-body">
        <div class="login-container">
            <el-card class="login-card" shadow="hover">
                <template #header>
                    <div class="card-header">管理员登录</div>
                </template>
                <div class="login-form">
                    <!-- 确保 el-form 的 model 正确绑定 -->
                    <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="100px">
                        <el-form-item label="账号：" prop="username">
                            <el-input type="text" v-model="loginForm.username" placeholder="请输入账号" clearable />
                        </el-form-item>
                        <el-form-item label="密码：" prop="password">
                            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" :loading="loading" @click="handleLogin">
                                登录
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </div>
    </div>
</template>


<script>
import axios from "axios";

export default {
    name: "AdminLogin",
    data() {
        return {
            loading: false,
            loginForm: {
                username: "",
                password: "",
            },
            loginRules: {
                username: [
                    { required: true, message: "请输入账号", trigger: "blur" },
                    { min: 3, max: 20, message: "账号长度在 3 到 20 个字符之间", trigger: "blur" },
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                    { min: 6, message: "密码长度至少为 6 个字符", trigger: "blur" },
                ],
            },
        };
    },
    methods: {
        async handleLogin() {
            this.$refs.loginFormRef.validate(async (valid) => {
                if (valid) {
                    this.loading = true;
                    try {
                        const formData = new URLSearchParams();
                        formData.append("username", this.loginForm.username);
                        formData.append("password", this.loginForm.password);

                        const response = await axios.post("/admins/login", formData, {
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded",
                            },
                        });
                        const adminData = response.data;
                        console.log("adminData : ", adminData);
                        if (adminData) {
                            // 假设需要将登录状态保存在 LocalStorage
                            localStorage.setItem("adminData", JSON.stringify(adminData));
                            this.$message.success("登录成功！");
                            this.$router.push("/"); // 跳转到管理员控制台页面
                        }
                    } catch (error) {
                        console.error(error);
                        this.$message.error(error.response?.data?.message || "登录失败，请重试");
                    } finally {
                        this.loading = false;
                    }
                } else {
                    this.$message.warning("请填写完整的登录信息");
                }
            });
        },
    },
};
</script>
