<template>
    <div>
        <HeadNar class="box"></HeadNar>
        <div class="parent">
            <LeftNar class="left"></LeftNar>
            <div class="right">
                <contextCard>
                    <router-view></router-view>
                </contextCard>
            </div>
        </div>
    </div>
</template>

<script>
import LeftNar from '../components/common/LeftNar.vue';
import HeadNar from '@/components/common/HeadNar.vue';
import contextCard from '@/components/common/contextCard.vue';

export default {
    name: "ViewIndex",
    components: {
        LeftNar,
        HeadNar,
        contextCard,

    },
    setup() {
        
    }
}

</script>
<style scoped>
.parent {
    display: flex;
}

.left {
    /* 设置左侧 div 占据空间 */
    flex: 15%;
    margin-top: 30px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
    /* 设置阴影 */
}

.right {
    /* 设置右侧 div 不占据空间 */
    flex: 85%;
    margin-top: 40px;
    margin-left: 10px;
}

.box {
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
    /* 设置阴影 */
}
</style>