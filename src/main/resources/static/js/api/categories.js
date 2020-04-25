import Vue from 'vue'

const expenses = Vue.resource('/expense/category')
const incomes = Vue.resource('/income/category')

export default {
    getExpenseCategories: () => expenses.get(),
    getIncomesCategories: () => incomes.get(),
}