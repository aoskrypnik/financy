<template>
    <v-expansion-panels v-model="panel" class="mb-2">
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
    import RecordDetail from "../records/RecordDetail.vue";
    import {mapGetters} from 'vuex'

    export default {
        props: ['recordsGroup'],
        computed: {
            ...mapGetters([
                'iconsMapGetter'
            ]),
            panel: {
                get() {
                    let panel = -1;
                    const id = this.$store.state.toBeExpanded;
                    for (let i = 0; i < this.recordsGroup.list.length; i++) {
                        if (this.recordsGroup.list[i].id === id) {
                            panel = 0
                            break
                        }
                    }
                    return panel;
                },
                set() {
                }
            },
            recordGroupBalance() {
                if (this.recordsGroup.list.length === 1) return this.recordsGroup.list[0].sum;
                let x = 0;
                for (let i = 0; i < this.recordsGroup.list.length; i++) {
                    x += this.recordsGroup.list[i].sum;
                }
                return x;
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