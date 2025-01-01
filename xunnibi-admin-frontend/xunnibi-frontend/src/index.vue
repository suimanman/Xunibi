<template>
  <el-container class="home-container">
    <el-header>
      <div class="header-left">
        <!-- <img src="" alt="logo" class="logo"> -->
        <span>虚拟币后台管理系统</span>
      </div>
      <!-- <el-button type="warning" plain @click="logout">退出</el-button> -->

      <span @click="logout" class="logout-button">
        <i class="el-icon-switch-button"></i>
        <span>退出账号</span>
      </span>
    </el-header>

    <el-container>
      <el-aside width="205px" class="menu-container">
        <el-menu background-color="#2e2e2e" text-color="#fff" active-text-color="#ffd04b" router="true">
          <el-menu-item index="/crypto" >
            <i class="el-icon-s-home"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>用户管理</span>

            </template>
            <el-menu-item index="/User">
              <i class="el-icon-s-custom"></i>
              <span>队员信息管理</span>
            </el-menu-item>
            <el-menu-item index="/team">
              <i class="el-icon-s-cooperation"></i>
              <span>团队信息管理</span>
            </el-menu-item>
            <el-menu-item index="/admin">
              <i class="el-icon-s-cooperation"></i>
              <span>管理员信息管理</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-s-goods"></i>
              <span>物品管理</span>
            </template>
            <el-menu-item index="/rent">
              <i class="el-icon-s-marketing"></i>
              <span>物品租用管理</span>
            </el-menu-item>
          </el-submenu>


          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-goods"></i>
              <span>审核管理</span>
            </template>
            <el-menu-item index="/achieverecords">
              <i class="el-icon-coin"></i>
              <span>材料审核记录</span>
            </el-menu-item>
            <el-menu-item index="/review">
              <i class="el-icon-document"></i>
              <span>材料审核管理</span>
            </el-menu-item>

          </el-submenu>
          <el-menu-item index="/notice">
            <i class="el-icon-coin"></i>
            <span>公告信息管理</span>
          </el-menu-item>

          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-goods"></i>
              <span>值日信息管理</span>
            </template>
            <el-menu-item index="/dutyrecord">
              <i class="el-icon-s-marketing"></i>
              <span>值日信息记录</span>
            </el-menu-item>
            <el-menu-item index="/dutyreview">
              <i class="el-icon-coin"></i>
              <span>值日信息审核</span>
            </el-menu-item>
          </el-submenu>

          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-s-goods"></i>
              <span>交易记录管理</span>
            </template>
            <el-menu-item index="/traderecords">
              <i class="el-icon-s-marketing"></i>
              <span>交易记录</span>
            </el-menu-item>
<!--            <el-menu-item index="/crypto">-->
<!--              <i class="el-icon-coin"></i>-->
<!--              <span>可视化信息管理</span>-->
<!--            </el-menu-item>-->
          </el-submenu>







        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';
export default {

  data() {
    return {
      menulist: [],
    };
  },
  created() {
    // this.getMenuList();
  },
  methods: {
    backToFirst() {
      this.$router.push({ path: '/' }).catch(err => {
        if (err.name !== 'NavigationDuplicated') {
          console.error(err);
        }
      });
    },

    logout() {
      window.sessionStorage.clear();
      this.$router.push({ path: 'login' });
    },
    // async getMenuList() {
    //   const { data: res } = await this.axios.get('menus');
    //   if (res.meta.status != 200) return this.$message.error(res.meta.msg);
    //   this.menulist = res.data;
    // },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  background-image: "xunnibi-frontend/src/assets/image/工学坊logo.png";
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  background-color: #fff;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 20px;
  color: #000;
  border-bottom: 2px solid #ddd;

  .header-left {
    display: flex;
    align-items: center;

    .logo {
      width: 40px;
      height: 40px;
      margin-right: 15px;
    }

    span {
      font-weight: bold;
      font-size: 22px;
    }
  }

  .el-button {
    font-size: 16px;
    color: #fff;
    border-color: #ff6b6b;

    &:hover {
      background-color: #ff6b6b;
      color: #fff;
    }
  }
}

.menu-container {
  background-color: #2e2e2e;
  min-width: 205px;
}

.el-menu {
  background-color: #4a90e2;

  .el-menu-item,
  .el-submenu__title {
    color: #ffffff;
    font-weight: bold;

    &:hover {
      background-color: #6fa2fd;
    }
  }

  .el-menu-item.is-active,
  .el-submenu__title.is-active {
    color: #ffd04b;
    background-color: #6fa2fd;
  }
}

.el-main {
  background-color: #f5f6fa;
  padding: 20px;
  overflow-y: auto;
}



.logout-button {
  display: flex;
  align-items: center;
  gap: 5px;
  /* 图标与文字之间的间距 */
  padding: 5px 10px;
  /* 内边距，增加点击区域 */
  font-size: 16px;
  /* 文字大小 */
  font-weight: bold;
  // /* 文字加粗 */
  // color: #ff4d4f;
  // /* 主色调为红色 */
  // border: 2px solid #ff4d4f;
  /* 边框颜色 */
  border-radius: 5px;
  /* 圆角边框 */
  cursor: pointer;
  /* 鼠标悬停变为手型 */
  transition: all 0.3s ease;
  /* 动画效果 */
}

.logout-button:hover {
  background-color: #ff4d4f;
  /* 鼠标悬停时背景变红 */
  color: #fff;
  /* 文字变为白色 */
  transform: scale(1.05);
  /* 轻微放大 */
}

.logout-button i {
  font-size: 18px;
  /* 图标稍大一些 */
}
</style>
