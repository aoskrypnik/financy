import Vue from 'vue'

const expenses = Vue.resource('/expense{/id}')

export default {
    save: expense => expenses.save({}, expense),
    update: expense => expenses.update({id: expense.id}, expense),
    remove: id => expenses.remove({id: id})
}