import Vue from 'vue'

const incomes = Vue.resource('/income{/id}')

export default {
    save: income => incomes.save({}, income),
    update: income => incomes.update({id: income.id}, income),
    remove: id => incomes.remove({id: id})
}