import Vue from 'vue'

const dates = Vue.resource('/record{/date}')
const bounds = Vue.resource('/record/bounds')
const balance = Vue.resource('/record/balance{/date}')

export default {
    getDate: date => dates.get({date: date}),
    getBounds: () => bounds.get(),
    getBalance: date => balance.get({date: date})
}