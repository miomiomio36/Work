<template>
    <div class="flex flex-wrap gap-4">
        
        <el-card shadow="always">
            <slot></slot>
        </el-card>
        
      </div>
</template>



<script>
export default {
    name: 'contextCard',
    
}

</script>



<style scoped>
.box{
    border: 1px solid #000000;
}
</style>