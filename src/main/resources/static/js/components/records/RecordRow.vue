<template>
    <v-expansion-panels class="mb-2">
        <v-expansion-panel>
            <v-expansion-panel-header>
                <i>{{recordsGroup.category}} {{recordsGroup.type}}</i>
                <template v-slot:actions>
                    <v-icon>expand_more</v-icon>
                </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
                <record-detail v-for="element in recordsGroup.list"
                               :key="element.id"
                               :record="element"
                               :type="recordsGroup.type"/>
            </v-expansion-panel-content>
        </v-expansion-panel>
    </v-expansion-panels>
</template>

<script>
    import RecordDetail from "components/records/RecordDetail.vue";

    export default {
        props: ['recordsGroup'],
        components: {
            RecordDetail
        },
    }
</script>

<style>

</style>