<template>
    <v-expansion-panels class="mb-2">
        <v-expansion-panel>
            <v-expansion-panel-header>
                <i>({{reformattedDate}}) {{record.sum}}</i>
                <template v-slot:actions>
                    <v-icon>expand_more</v-icon>
                </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
                <v-card>
                    <v-card-actions><b>{{record.category}}</b></v-card-actions>
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
            </v-expansion-panel-content>
        </v-expansion-panel>
    </v-expansion-panels>
</template>

<script>
    import {mapActions} from 'vuex'

    function reformatDate(creationDate) {
        return new Date(creationDate).toLocaleDateString()
    }

    export default {
        props: ['record'],
        methods: {
            ...mapActions(['removeExpenseAction', 'removeIncomeAction']),
            del() {
                if (this.record.type === 'income') {
                    this.removeIncomeAction(this.record)
                } else if (this.record.type === 'expense') {
                    this.removeExpenseAction(this.record)
                }
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