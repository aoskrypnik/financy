<template>
    <v-col cols="12">
        <v-row justify="space-around">
            <v-bottom-sheet v-model="expenseSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" x-large fab dark color="red">
                        <v-icon dark>euro</v-icon>
                    </v-btn>
                </template>
                <v-sheet class="text-center">
                    <v-col cols="12">
                        <v-layout column mt="2">
                            <v-text-field label="Sum" placeholder="Write sum" v-model="expenseSum"/>
                            <v-text-field label="Category" placeholder="Write category" v-model="expenseCategory"/>
                            <v-text-field label="Comment" placeholder="Write comment" v-model="expenseComment"/>
                            <v-btn class="mt-6" text color="error" @click="saveExpense">Save</v-btn>
                            <v-btn class="mt-6" text color="primary" @click="expenseSheet=!expenseSheet">Close</v-btn>
                        </v-layout>
                    </v-col>
                </v-sheet>
            </v-bottom-sheet>
            <v-bottom-sheet v-model="incomeSheet" persistent inset>
                <template v-slot:activator="{ on }">
                    <v-btn v-on="on" class="mx-2" x-large fab dark color="green">
                        <v-icon dark>euro</v-icon>
                    </v-btn>
                </template>
                <v-sheet class="text-center">
                    <v-col cols="12">
                        <v-layout column mt="2">
                            <v-text-field label="Sum" placeholder="Write sum" v-model="incomeSum"/>
                            <v-text-field label="Category" placeholder="Write category" v-model="incomeCategory"/>
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
            return {
                expenseSum: '',
                expenseCategory: '',
                expenseComment: '',
                expenseSheet: false,
                incomeSum: '',
                incomeCategory: '',
                incomeComment: '',
                incomeSheet: false
            }
        },
        methods: {
            saveIncome() {
                const record = {sum: this.incomeSum, category: this.incomeCategory, comment: this.incomeComment};
                this.$resource('/income{/id}').save({}, record).then(result =>
                    result.json().then(data => {
                        this.records.push(data);
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

</style>