<template>
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo mm"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
      >
        <el-menu-item index="0" >
          <span class="font1">考试管理系统</span>
        </el-menu-item>
        <div class="flex-grow" />
        
        <el-sub-menu index="1">
          <template #title >
            <img src="../../assets/img/userimg.png" alt="" class="timg">
            <span class="font1">{{user.name}}</span>
            </template>
            <router-link :to="rout">
          <el-menu-item index="2-1">切换账号</el-menu-item>
          </router-link>
          </el-sub-menu>
      </el-menu>
    
</template>

<script>
import { ref, defineComponent } from 'vue';
import { GetUserData } from '@/auth/auth.js'
export default defineComponent({
  name: "HeadNar",
  
  setup() {
    const activeIndex = ref('1')
    const activeIndex2 = ref('1')
    const user = GetUserData();
    const rout = '/'
    
    return {
      activeIndex,
      activeIndex2,
      user,
      rout,
    }
  }
});

</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
.timg{
    width: 50px;
    height: 50px;
    border-radius: 100%;
}
.mm{
    background-color:  #ffffff;
}

.font1{
    font:50px;
    color:#000000;
}
</style>
