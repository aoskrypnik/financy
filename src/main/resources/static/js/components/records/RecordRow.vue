<template>
    <v-card class="my-2">
        <v-card-title>
            <div><i>({{reformattedDate}})</i> {{record.sum}}<b> {{record.category}}</b></div>
        </v-card-title>
        <v-card-actions>
            <v-btn icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    function reformatDate(creationDate) {
        return new Date(creationDate).toLocaleDateString()
    }

    export default {
        props: ['record', 'deleteRecord', 'records'],
        methods: {
            del() {
                this.deleteRecord(this.record)
            }
        },
        data() {
            return {
                reformattedDate: reformatDate(this.record.creationDate)
            }
        }
    }
</script>

<style>

</style>