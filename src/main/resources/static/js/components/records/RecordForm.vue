<template>
    <v-col cols="12">
        <record-date></record-date>
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
                            <v-select v-model="expenseCategory" :items="expenseCategoriesGetter"
                                      label="Choose category"></v-select>
                            <v-text-field label="Comment" placeholder="Write comment" v-model="expenseComment"/>
                            <v-menu
                                    ref="menu1"
                                    v-model="menu1"
                                    :close-on-content-click="false"
                                    transition="scale-transition"
                                    offset-y
                                    max-width="290px"
                                    min-width="290px"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                            v-model="creationDate"
                                            label="Date"
                                            hint="YYYY-MM-DD format"
                                            persistent-hint
                                            v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="creationDate" no-title @input="menu1 = false"></v-date-picker>
                            </v-menu>
                            <v-btn class="mt-6" text color="error" @click="saveExpense">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="close(true)">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
            <v-btn large outlined :color="balanceColorGetter"
                   :class="{'disable-events': true}" height="56px" max-width="146px">
                Balance {{balanceGetter}}€
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
                            <v-select v-model="incomeCategory" :items="incomeCategoriesGetter"
                                      label="Choose category"></v-select>
                            <v-text-field label="Comment" placeholder="Write comment" v-model="incomeComment"/>
                            <v-menu
                                    ref="menu1"
                                    v-model="menu2"
                                    :close-on-content-click="false"
                                    transition="scale-transition"
                                    offset-y
                                    max-width="290px"
                                    min-width="290px"
                            >
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                            v-model="creationDate"
                                            label="Date"
                                            hint="YYYY-MM-DD format"
                                            persistent-hint
                                            v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="creationDate" no-title @input="menu2 = false"></v-date-picker>
                            </v-menu>
                            <v-btn class="mt-6" text color="error" @click="saveIncome">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="close(false)">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
        </v-row>
    </v-col>
</template>

<script>
    import RecordDate from "components/records/RecordDate.vue";
    import {mapActions, mapGetters} from 'vuex'

    export default {
        components: {
            RecordDate
        },
        data() {
            return {
                id: null,
                expenseSum: '',
                expenseCategory: '',
                expenseComment: '',
                expenseSheet: false,
                incomeSum: '',
                incomeCategory: '',
                incomeComment: '',
                incomeSheet: false,
                creationDate: new Date().toISOString().substr(0, 10),
                menu1: false,
                menu2: false,
            }
        },
        computed: {
            ...mapGetters([
                'balanceGetter',
                'balanceColorGetter',
                'incomeCategoriesGetter',
                'expenseCategoriesGetter',
            ]),
            editableRec() {
                return this.$store.state.editableRecord;
            }
        },
        watch: {
            editableRec(newVal, oldVal) {
                if (newVal['type'] === 'Expense') {
                    this.id = newVal['id']
                    this.expenseCategory = newVal['category']
                    this.expenseSum = newVal['sum']
                    this.creationDate = newVal['creationDate']
                    this.expenseComment = newVal['comment']
                    this.expenseSheet = !this.expenseSheet
                } else if (newVal['type'] === 'Income') {
                    this.id = newVal['id']
                    this.incomeCategory = newVal['category']
                    this.incomeSum = newVal['sum']
                    this.creationDate = newVal['creationDate']
                    this.incomeComment = newVal['comment']
                    this.incomeSheet = !this.incomeSheet
                }
            }
        },
        methods: {
            ...mapActions([
                'addIncomeAction',
                'addExpenseAction',
                'recalculateBalanceAction',
                'editIncomeAction',
                'editExpenseAction'
            ]),
            saveIncome() {
                const record = {
                    id: this.id,
                    sum: this.incomeSum,
                    category: this.incomeCategory,
                    comment: this.incomeComment,
                    creationDate: this.creationDate
                };
                if (this.id === null)
                    this.addIncomeAction(record)
                else
                    this.editIncomeAction(record)
                this.incomeSum = '';
                this.incomeCategory = '';
                this.incomeComment = '';
                this.close(false);
            },
            saveExpense() {
                const record = {
                    id: this.id,
                    sum: this.expenseSum,
                    category: this.expenseCategory,
                    comment: this.expenseComment,
                    creationDate: this.creationDate
                };
                if (this.id === null)
                    this.addExpenseAction(record)
                else
                    this.editExpenseAction(record)
                this.expenseSum = '';
                this.expenseCategory = '';
                this.expenseComment = '';
                this.close(true);
            },
            close(isExpense) {
                this.id = null;
                isExpense
                    ? this.expenseSheet = !this.expenseSheet
                    : this.incomeSheet = !this.incomeSheet;
                this.creationDate = new Date().toISOString().substr(0, 10);
                this.menu1 = false;
                this.menu2 = false;
            }
        },
        created() {
            this.$store.dispatch('getIncomeCategoriesAction');
            this.$store.dispatch('getExpenseCategoriesAction');
            this.$store.dispatch('createDatesListAction', new Date());
        },
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