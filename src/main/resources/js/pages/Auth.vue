<template>
    <v-container v-if="!profile">You should authorize with
        <a href="/login">Google</a>
    </v-container>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        computed: mapState(['profile']),
    }
</script>

<style scoped>

</style>