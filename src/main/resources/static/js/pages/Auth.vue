<template>
    <v-container v-if="!profile">You should authorize with
        <a href="/login">Google</a>
    </v-container>
</template>

<script>
    export default {
        name: "Auth"
    }
</script>

<style scoped>

</style>