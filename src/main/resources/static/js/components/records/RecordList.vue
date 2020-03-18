<template>
    <v-layout align-content-space-around justify-start column>
        <record-form :records="sortedRecords"/>
        <record-row v-for="record in sortedRecords"
                    :key="record.id"
                    :record="record"
                    :records="records"
                    :deleteIncome="deleteIncome"
                    :deleteExpense="deleteExpense"/>
    </v-layout>
</template>

<script>
    import RecordRow from "components/records/RecordRow.vue";
    import RecordForm from "components/records/RecordForm.vue";

    export default {
        props: ['records'],
        components: {
            RecordRow,
            RecordForm
        },
        data() {
            return {
                record: null
            }
        },
        computed: {
            sortedRecords() {
                return this.records.sort((a, b) => -(new Date(a.creationDate) - new Date(b.creationDate)))
            }
        },
        methods: {
            deleteIncome(record) {
                this.$resource('/income{/id}').remove({id: record.id}).then(result => {
                    if (result.ok) {
                        this.records.splice(this.records.indexOf(record), 1)
                    }
                })
            },
            deleteExpense(record) {
                this.$resource('/expense{/id}').remove({id: record.id}).then(result => {
                    if (result.ok) {
                        this.records.splice(this.records.indexOf(record), 1)
                    }
                })
            }
        }
    }
</script>

<style>

</style>