<template>
    <v-container v-if="!profile">You should authorize with
        <a href="/login">Google</a>
    </v-container>
    <v-container v-else>
        You are already logged in
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        computed: mapState(['profile']),
        beforeMount() {
            if (this.profile) {
                this.$router.replace('/')
            }
        }
    }

</script>

<style scoped>

</style>