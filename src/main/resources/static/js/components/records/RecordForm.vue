<template>
    <v-col cols="12">
        <v-row class="justify-center mb-3">
            <v-btn x-small class="mr-2 transparent"
                   :disabled="isCurrentDateFirstInList(curDate.dateString, dateListGetter)"
                   @click="moveDateLeft">
                <v-icon>keyboard_arrow_left</v-icon>
            </v-btn>
            <v-data>{{curDate.dateString}}</v-data>
            <v-btn x-small class="ml-2 transparent"
                   :disabled="isCurrentDateLastInList(curDate.dateString, dateListGetter)"
                   @click="moveDateRight">
                <v-icon>keyboard_arrow_right</v-icon>
            </v-btn>
        </v-row>
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
                            <v-btn class="mt-6" text color="error" @click="saveExpense">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="expenseSheet=!expenseSheet">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
            <v-btn large outlined :color="balanceColorGetter"
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
                            <v-select v-model="incomeCategory" :items="incomeCategoriesGetter"
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
            return {
                expenseSum: '',
                expenseCategory: '',
                expenseComment: '',
                expenseSheet: false,
                incomeSum: '',
                incomeCategory: '',
                incomeComment: '',
                incomeSheet: false,
            }
        },
        computed: {
            ...mapGetters([
                'balanceGetter',
                'balanceColorGetter',
                'incomeCategoriesGetter',
                'expenseCategoriesGetter',
                'dateListGetter'
            ]),
            curDate() {
                let date = new Date();
                const ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(date);
                const mo = new Intl.DateTimeFormat('en', {month: 'short'}).format(date);
                let tempRes = {dateString: ye + ' ' + mo};
                let curDate = this.$store.getters.dateListGetter.find(e => e.isCurrent === true);
                console.log('cur from component ' + (curDate === undefined ? 'sex' : curDate.dateFormat));
                return curDate === undefined ? tempRes : curDate
            }
        },
        methods: {
            ...mapActions([
                'addIncomeAction',
                'addExpenseAction',
                'recalculateBalanceAction',
                'moveDateLeftAction',
                'moveDateRightAction'
            ]),
            saveIncome() {
                const record = {sum: this.incomeSum, category: this.incomeCategory, comment: this.incomeComment};
                this.addIncomeAction(record);
                this.incomeSum = '';
                this.incomeCategory = '';
                this.incomeComment = '';
                this.incomeSheet = !this.incomeSheet
            },
            saveExpense() {
                const record = {sum: this.expenseSum, category: this.expenseCategory, comment: this.expenseComment};
                this.addExpenseAction(record);
                this.expenseSum = '';
                this.expenseCategory = '';
                this.expenseComment = '';
                this.expenseSheet = !this.expenseSheet
            },
            isCurrentDateFirstInList(current, list) {
                if (list[0] === undefined) return true;
                return list[0].dateString === current;
            },
            isCurrentDateLastInList(current, list) {
                if (list[list.length - 1] === undefined) return true;
                return list[list.length - 1].dateString === current;
            },
            moveDateLeft() {
                const leftDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) - 1];
                console.log('left ' + leftDate.dateFormat);
                this.moveDateLeftAction(leftDate);
            },
            moveDateRight() {
                const rightDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) + 1];
                console.log('right ' + rightDate.dateFormat);
                this.moveDateRightAction(rightDate);
            }
        },
        created() {
            this.$store.dispatch('getIncomeCategoriesAction');
            this.$store.dispatch('getExpenseCategoriesAction');
            this.$store.dispatch('createDatesListAction');
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