import Vue from 'vue';
import VueResource from 'vue-resource'
import Vuex from 'vuex';

Vue.use(VueResource);
Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        incomes: frontendData.incomes,
        expenses: frontendData.expenses,
        profile: frontendData.profile,
        balance: frontendData.balance,
    },
    getters: {
        sortedRecords: state => {
            let incomes = state.incomes ? state.incomes : [];
            incomes.forEach(function (element) {
                element["type"] = "income"
            });
            let expenses = state.expenses ? state.expenses : [];
            expenses.forEach(function (element) {
                element["type"] = "expense"
            });
            let records = incomes.concat(expenses);
            return records.sort((a, b) => -(new Date(a.creationDate) - new Date(b.creationDate)))
        },
        balanceGetter: state => {
            return state.balance
        }
    },
    mutations: {
        addIncomeMutation(state, income) {
            state.incomes = [
                ...state.incomes,
                income
            ]
        },
        addExpenseMutation(state, expense) {
            state.expenses = [
                ...state.expenses,
                expense
            ]
        },
        removeIncomeMutation(state, income) {
            const deletionIndex = state.incomes.findIndex(item => item.id === income.id);
            if (deletionIndex > -1) {
                state.incomes = [
                    ...state.incomes.slice(0, deletionIndex),
                    ...state.incomes.slice(deletionIndex + 1)
                ]
            }
        },
        removeExpenseMutation(state, expense) {
            const deletionIndex = state.expenses.findIndex(item => item.id === expense.id);
            if (deletionIndex > -1) {
                state.expenses = [
                    ...state.expenses.slice(0, deletionIndex),
                    ...state.expenses.slice(deletionIndex + 1)
                ]
            }
        },
        recalculateBalanceMutation(state, newBalance) {
            state.balance = parseInt(newBalance);
        }
    },
    actions: {
        async addIncomeAction({commit, dispatch}, record) {
            const result = await Vue.resource('/income{/id}').save({}, record);
            const data = await result.json();
            commit('addIncomeMutation', data);
            dispatch('recalculateBalanceAction')
        },
        async addExpenseAction({commit, dispatch}, record) {
            const result = await Vue.resource('/expense{/id}').save({}, record);
            const data = await result.json();
            commit('addExpenseMutation', data);
            dispatch('recalculateBalanceAction')
        },
        async removeIncomeAction({commit, dispatch}, income) {
            const result = await Vue.resource('/income{/id}').remove({id: income.id});
            if (result.ok) {
                commit('removeIncomeMutation', income);
                dispatch('recalculateBalanceAction')
            }
        },
        async removeExpenseAction({commit, dispatch}, expense) {
            const result = await Vue.resource('/expense{/id}').remove({id: expense.id});
            if (result.ok) {
                commit('removeExpenseMutation', expense);
                dispatch('recalculateBalanceAction')
            }
        },
        async recalculateBalanceAction({commit}) {
            const currentDate = new Date();
            const path = '/statistic/balance/' + currentDate.toISOString().substring(0, 10);
            const result = await Vue.resource(path).get();
            const data = await result.json();
            commit('recalculateBalanceMutation', data)
        }
    }
})