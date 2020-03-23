<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Financy</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container v-if="!profile">You should authorize with
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <record-list/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import RecordList from "../components/records/RecordList.vue"
    import {mapState} from 'vuex'

    export default {
        components: {
            RecordList
        },
        computed: mapState(['profile'])
    }
</script>

<style>

</style>