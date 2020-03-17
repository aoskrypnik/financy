<template>
    <v-card class="my-2">
        <v-card-title>
            <div><i>({{record.creationDate}})</i> {{record.sum}}<b> {{record.category}}</b></div>
        </v-card-title>
        <v-card-actions>
            <v-btn icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        props: ['record', 'deleteRecord', 'records'],
        methods: {
            del() {
                this.deleteRecord(this.record)
            }
        }
    }
</script>

<style>

</style>