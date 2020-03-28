<template>
    <v-card class="mb-2">
        <v-card-actions><b>{{record.sum}}</b></v-card-actions>
        <v-card-actions v-if="record.comment !== ''">• {{record.comment}}</v-card-actions>
        <v-card-actions>
            <v-btn icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
            <v-btn disabled icon small>
                <v-icon>edit</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'

    function reformatDate(creationDate) {
        return new Date(creationDate).toLocaleDateString()
    }

    export default {
        props: ['record', 'type'],
        methods: {
            ...mapActions(['removeExpenseAction', 'removeIncomeAction']),
            del() {
                if (this.type === 'Income') {
                    this.removeIncomeAction(this.record)
                } else if (this.type === 'Expense') {
                    this.removeExpenseAction(this.record)
                }
            }
        },
    }
</script>

<style>

</style>