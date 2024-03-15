<template>
    <div class="mm tac">
            <el-menu :default-active="ac_index" class="el-menu-vertical-demo mm" @open="handleOpen" @close="handleClose" @select="select1">
                <div v-for="(item, index) in menu" :key="index" class="mm">
                    <!-- <el-menu-item :index="item.id">
                        <el-icon>
                            <component :is="item.icon"></component>
                        </el-icon>
                        <span>{{ item.title }}</span>
                    </el-menu-item> -->

                    <el-sub-menu :index="item.id" >
                        <template #title>
                            <el-icon>
                                <component :is="item.icon"></component>
                            </el-icon>
                            <span class="font1">{{ item.title }}</span>
                        </template>
                        <div v-for="(two, index_2) in item.SubClass" :key="index_2">
                            <el-menu-item :index="two.id" class="font1 m2" >
                            <router-link :to="two.router" class="font1 m2">
                                ->{{ two.title }}
                            </router-link>
                            </el-menu-item>
                        </div>

                    </el-sub-menu>
                </div>

            </el-menu>

    </div>
</template>

<script>
import {
    UserFilled,
    EditPen,
    Document,
    Setting,
} from '@element-plus/icons-vue'
import { GetUserData } from '@/auth/auth.js'
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex';

export default {
    
    name: "LeftNar",
    components: {
        UserFilled,
        EditPen,
        Document,
        Setting,
    },


    setup() {
        // 获取当前角色
        const user = GetUserData();

        const store = useStore(); // 获取 Vuex store

        // const menu = store.state.menu_teacher;

        let menu = ref([]);
        
        if (user.role == 'teacher') {
            menu = store.state.menu_teacher   // 获取菜单
        }
        else if (user.role == 'student') {
            menu = store.state.menu_student   // 获取菜单
        }
        else {
            menu = store.state.menu_admin
        }
        // 保存上一次点击位置:
        const ac_index = ref('1')
        function select1(index){
            localStorage.setItem('menuid',JSON.stringify(index))
        }

        onMounted(() => {
            ac_index.value = JSON.parse(localStorage.getItem('menuid'))
            
        })

        return {
            menu,ac_index,select1,
        }
    }
}

</script>
<style scoped>
.tac {
    width: 10%;
    height: auto;
}
.mm{
    background-color: #ffffff;

}
.font1{
    font:50px;
    color:#000000;
}
.m2{
    background-color: #f8f8f8dd;
    
}
.el-menu-item.is-active {
    color: rgb(0, 134, 251);
}
</style>