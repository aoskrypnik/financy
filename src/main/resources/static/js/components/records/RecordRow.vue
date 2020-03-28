<template>
    <v-expansion-panels class="mb-2">
        <v-expansion-panel>
            <v-expansion-panel-header>
                <i>{{recordsGroup.category}} {{recordsGroup.type}} {{recordGroupBalance}}</i>
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
        computed: {
            recordGroupBalance() {
                if (this.recordsGroup.list.length === 1) return this.recordsGroup.list[0].sum;
                return this.recordsGroup.list.reduce((a, b) => a.sum + b.sum)
            }
        },
        components: {
            RecordDetail
        },
    }
</script>

<style>

</style>