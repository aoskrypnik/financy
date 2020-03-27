<template>
    <v-layout align-content-space-around justify-start column>
        <record-form/>
        <record-row v-for="recordsGroup in sortedRecords"
                    :key="recordsGroup.category+recordsGroup.type"
                    :recordsGroup="recordsGroup"/>
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
        computed: mapGetters(['sortedRecords'])
    }
</script>

<style>

</style>