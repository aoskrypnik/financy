<template>
    <v-col cols="12">
        <v-row justify="space-around">
            <v-bottom-sheet v-model="expenseSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" large fab dark color="red">
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
            <v-btn large outlined :color="balanceColor.length === 0 ? 'grey' : balanceColor[0]"
                   :class="{'disable-events': true}" height="64px">
                Balance {{balanceList.length === 0 ? 0 : balanceList[0]}}
            </v-btn>
            <v-bottom-sheet v-model="incomeSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" large fab dark color="green">
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
    export default {
        props: ['records'],
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
            let balanceList = [];
            let balanceColor = [];
            this.countBalance(balanceList, balanceColor, this.defineBalanceColor);
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
                balanceList: balanceList,
                balanceColor: balanceColor
            }
        },
        methods: {
            countBalance(balanceList, balanceColor, callback) {
                this.$resource('/statistic/balance/2020-03-01').get().then(result => {
                    result.json().then(data => {
                        balanceList.push(data);
                        balanceColor.push(callback());
                    })
                });
            },
            defineBalanceColor() {
                if (this.balanceList.length === 0 || this.balanceList[0] === 0) return 'grey';
                if (this.balanceList[0] > 0) return 'green';
                if (this.balanceList[0] < 0) return 'red';
            },
            saveIncome() {
                const record = {sum: this.incomeSum, category: this.incomeCategory, comment: this.incomeComment};
                this.$resource('/income{/id}').save({}, record).then(result =>
                    result.json().then(data => {
                        this.records.push(data);
                        this.balanceList[0] += parseInt(this.incomeSum);
                        this.balanceColor[0] = this.defineBalanceColor();
                        this.incomeSum = '';
                        this.incomeCategory = '';
                        this.incomeComment = '';
                    }));
                this.incomeSheet = !this.incomeSheet
            },
            saveExpense() {
                const record = {sum: this.expenseSum, category: this.expenseCategory, comment: this.expenseComment};
                this.$resource('/expense{/id}').save({}, record).then(result =>
                    result.json().then(data => {
                        this.records.push(data);
                        this.balanceList[0] -= parseInt(this.expenseSum);
                        this.balanceColor[0] = this.defineBalanceColor();
                        this.expenseSum = '';
                        this.expenseCategory = '';
                        this.expenseComment = '';
                    }));
                this.expenseSheet = !this.expenseSheet
            },
        }
    }
</script>

<style>
    .disable-events {
        pointer-events: none;
    }
</style>