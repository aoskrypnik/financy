<template>
    <v-layout align-content-space-around justify-start column>
        <record-form/>
        <record-row v-for="record in sortedRecords"
                    :key="record.id"
                    :record="record"/>
    </v-layout>
</template>

<script>
    import RecordRow from "components/records/RecordRow.vue";
    import RecordForm from "components/records/RecordForm.vue";
    import {mapGetters} from 'vuex';

    export default {
        components: {
            RecordRow,
            RecordForm
        },
        data() {
            return {
                record: null
            }
        },
        computed: mapGetters(['sortedRecords'])
    }
</script>

<style>

</style>