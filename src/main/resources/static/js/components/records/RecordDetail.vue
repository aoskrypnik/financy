<template>
    <v-card class="mb-2">
        <v-row>
            <v-col class="ml-1">
                <v-icon x-small :color="color">trip_origin</v-icon>
                {{record.sum}}€
            </v-col>
            <v-col>
                <v-layout class="float-right">
                    <v-btn icon @click="del" small>
                        <v-icon>delete</v-icon>
                    </v-btn>
                </v-layout>
                <v-layout class="float-right mr-2"><i>({{reformatDate(record.creationDate)}})</i></v-layout>
            </v-col>
        </v-row>
        <v-row v-if="record.comment !== ''">
            <v-col class="ml-1 pt-0">
                <v-btn icon x-small>
                    <v-icon>comment</v-icon>
                </v-btn>
                <i> {{record.comment}}</i>
            </v-col>
        </v-row>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        props: ['record', 'type', 'color'],
        methods: {
            ...mapActions(['removeExpenseAction', 'removeIncomeAction']),
            del() {
                if (this.type === 'Income') {
                    this.removeIncomeAction(this.record)
                } else if (this.type === 'Expense') {
                    this.removeExpenseAction(this.record)
                }
            },
            reformatDate(creationDate) {
                let date = new Date(creationDate);
                const ye = new Intl.DateTimeFormat('en', {day: 'numeric'}).format(date);
                const mo = new Intl.DateTimeFormat('en', {month: 'short'}).format(date);
                return ye + ' ' + mo;
            }
        },
    }
</script>

<style>

</style>