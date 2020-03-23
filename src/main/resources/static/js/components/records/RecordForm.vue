<template>
    <v-col cols="12">
        <v-row justify="space-around">
            <v-bottom-sheet v-model="expenseSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" fab dark color="red">
                        <v-icon dark>euro</v-icon>
                    </v-btn>
                </template>
                <v-sheet class="text-center">
                    <v-col cols="12">
                        <v-layout column mt="2">
                            <v-text-field label="Sum" placeholder="Write sum" v-model="expenseSum"/>
                            <v-select v-model="expenseCategory" :items="expenseCategories"
                                      label="Choose category"></v-select>
                            <v-text-field label="Comment" placeholder="Write comment" v-model="expenseComment"/>
                            <v-btn class="mt-6" text color="error" @click="saveExpense">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="expenseSheet=!expenseSheet">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
            <v-btn large outlined
                   :class="{'disable-events': true}" height="56px" max-width="146px">
                Balance {{balanceGetter}}
            </v-btn>
            <v-bottom-sheet v-model="incomeSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" fab dark color="green">
                        <v-icon dark>euro</v-icon>
                    </v-btn>
                </template>
                <v-sheet class="text-center">
                    <v-col cols="12">
                        <v-layout column mt="2">
                            <v-text-field label="Sum" placeholder="Write sum" v-model="incomeSum"/>
                            <v-select v-model="incomeCategory" :items="incomeCategories"
                                      label="Choose category"></v-select>
                            <v-text-field label="Comment" placeholder="Write comment" v-model="incomeComment"/>
                            <v-btn class="mt-6" text color="error" @click="saveIncome">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="incomeSheet=!incomeSheet">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
        </v-row>
    </v-col>
</template>

<script>
    import {mapActions, mapGetters} from 'vuex'

    export default {
        props: [],
        data() {
            let incomeCategories = [];
            this.$resource('/income/category').get().then(result =>
                result.json().then(data => {
                    data.forEach(e => incomeCategories.push(e));
                }));
            let expenseCategories = [];
            this.$resource('/expense/category').get().then(result =>
                result.json().then(data => {
                    data.forEach(e => expenseCategories.push(e));
                }));
            return {
                expenseSum: '',
                expenseCategory: '',
                expenseComment: '',
                expenseSheet: false,
                incomeSum: '',
                incomeCategory: '',
                incomeComment: '',
                incomeSheet: false,
                incomeCategories: incomeCategories,
                expenseCategories: expenseCategories,
            }
        },
        computed: {
            ...mapGetters(['balanceGetter'])
        },
        methods: {
            ...mapActions(['addIncomeAction', 'addExpenseAction', 'recalculateBalanceAction']),
            saveIncome() {
                const record = {sum: this.incomeSum, category: this.incomeCategory, comment: this.incomeComment};
                this.addIncomeAction(record);
                this.recalculateBalanceAction();
                this.incomeSum = '';
                this.incomeCategory = '';
                this.incomeComment = '';
                this.incomeSheet = !this.incomeSheet
            },
            saveExpense() {
                const record = {sum: this.expenseSum, category: this.expenseCategory, comment: this.expenseComment};
                this.addExpenseAction(record);
                this.recalculateBalanceAction();
                this.expenseSum = '';
                this.expenseCategory = '';
                this.expenseComment = '';
                this.expenseSheet = !this.expenseSheet
            },
        }
    }
</script>

<style>
    .disable-events {
        pointer-events: none;
    }

    .v-btn {
        min-width: 0;
    }
</style>