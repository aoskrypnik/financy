<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Financy</v-toolbar-title>
            <v-btn
                    text
                    :disabled="$route.path === '/'"
                    v-if="profile"
                    @click="showList">
                List
            </v-btn>
            <v-btn
                    text
                    :disabled="$route.path === '/chart'"
                    v-if="profile"
                    @click="showChart">
                Chart
            </v-btn>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: {
            showList() {
                this.$router.push('/')
            },
            showChart() {
                this.$router.push('/chart')
            }
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }

</script>

<style>

</style>