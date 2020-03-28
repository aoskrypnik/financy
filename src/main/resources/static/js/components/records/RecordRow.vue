<template>
    <v-expansion-panels class="mb-2">
        <v-expansion-panel>
            <v-expansion-panel-header>
                <v-col class="pa-0" cols="8">
                    <v-icon class="mr-2">{{iconsMapGetter[recordsGroup.category+recordsGroup.type]}}</v-icon>
                    <b class="text-left">{{recordsGroup.category}} ({{recordsGroup.list.length}})</b>
                </v-col>
                <v-col cols="4">
                    <v-layout class="float-right" :style="{ 'color': groupColor }">{{recordGroupBalance}}€</v-layout>
                </v-col>
                <template v-slot:actions>
                    <v-icon>expand_more</v-icon>
                </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
                <record-detail v-for="element in recordsGroup.list"
                               :key="element.id"
                               :record="element"
                               :type="recordsGroup.type"
                               :color="groupColor"/>
            </v-expansion-panel-content>
        </v-expansion-panel>
    </v-expansion-panels>
</template>

<script>
    import RecordDetail from "components/records/RecordDetail.vue";
    import {mapGetters} from 'vuex'

    export default {
        props: ['recordsGroup'],
        computed: {
            ...mapGetters([
                'iconsMapGetter'
            ]),
            recordGroupBalance() {
                if (this.recordsGroup.list.length === 1) return this.recordsGroup.list[0].sum;
                return this.recordsGroup.list.reduce((a, b) => a.sum + b.sum)
            },
            groupColor() {
                return (this.recordsGroup.type === 'Expense') ? 'red' : 'green'
            }
        },
        components: {
            RecordDetail
        },
    }
</script>

<style>

</style>