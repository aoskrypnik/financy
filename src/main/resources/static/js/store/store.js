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
        expenseCategories: [],
        incomeCategories: [],
        dateList: [],
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
            return records.sort(function (a, b) {
                if (new Date(a.creationDate) - new Date(b.creationDate) === 0) {
                    return -(a.id - b.id)
                }
                return -(new Date(a.creationDate) - new Date(b.creationDate))
            })
        },
        balanceGetter: state => {
            return state.balance
        },
        balanceColorGetter: state => {
            if (state.balance < 0) return 'red';
            else if (state.balance > 0) return 'green';
            else return 'grey'
        },
        incomeCategoriesGetter: state => {
            return state.incomeCategories;
        },
        expenseCategoriesGetter: state => {
            return state.expenseCategories;
        },
        dateListGetter: state => {
            return state.dateList;
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
        },
        getExpenseCategoriesMutation(state, expenseCategories) {
            state.expenseCategories = expenseCategories
        },
        getIncomeCategoriesMutation(state, incomeCategories) {
            state.incomeCategories = incomeCategories
        },
        createDatesListMutation(state, datesMap) {
            const curDate = new Date();
            let first = new Date(datesMap.first);
            first.setDate(2);
            let last = new Date(datesMap.last);
            last.setDate(2);
            while (first <= last) {
                const ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(first);
                const mo = new Intl.DateTimeFormat('en', {month: 'short'}).format(first);
                const cur = curDate.getFullYear() === first.getFullYear() && curDate.getMonth() === first.getMonth();
                state.dateList.push({
                    dateString: mo + ' ' + ye,
                    dateFormat: first.toISOString().split('T')[0],
                    isCurrent: cur
                });
                first.setMonth(first.getMonth() + 1)
            }
        },
        getNewRecordsMutation(state, payload) {
            state.incomes = payload.data.incomes;
            state.expenses = payload.data.expenses;
            const curIndex = state.dateList.findIndex(e => e.isCurrent === true);
            state.dateList[curIndex].isCurrent = false;
            payload.moveLeft
                ? state.dateList[curIndex - 1].isCurrent = true
                : state.dateList[curIndex + 1].isCurrent = true
        }
    },
    actions: {
        async addIncomeAction({commit, dispatch}, record) {
            const result = await Vue.resource('/income{/id}').save({}, record);
            const data = await result.json();
            commit('addIncomeMutation', data);
            dispatch('recalculateBalanceAction', record.creationDate)
        },
        async addExpenseAction({commit, dispatch}, record) {
            const result = await Vue.resource('/expense{/id}').save({}, record);
            const data = await result.json();
            commit('addExpenseMutation', data);
            dispatch('recalculateBalanceAction', record.creationDate)
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
        async recalculateBalanceAction({commit}, date) {
            const path = '/record/balance/' + date;
            const result = await Vue.resource(path).get();
            const data = await result.json();
            commit('recalculateBalanceMutation', data)
        },
        async getIncomeCategoriesAction({commit}) {
            let incomeCategories = [];
            const result = await Vue.resource('/income/category').get();
            const data = await result.json();
            data.forEach(e => incomeCategories.push(e));
            commit('getIncomeCategoriesMutation', incomeCategories)
        },
        async getExpenseCategoriesAction({commit}) {
            let expenseCategories = [];
            const result = await Vue.resource('/expense/category').get();
            const data = await result.json();
            data.forEach(e => expenseCategories.push(e));
            commit('getExpenseCategoriesMutation', expenseCategories)
        },
        async createDatesListAction({commit}) {
            const result = await Vue.resource('/record/bounds').get();
            const data = await result.json();
            commit('createDatesListMutation', data)
        },
        async moveDateLeftAction({commit, dispatch}, date) {
            const result = await Vue.resource('record/' + date.dateFormat).get();
            const data = await result.json();
            const payload = {
                data: data,
                moveLeft: true
            };
            commit('getNewRecordsMutation', payload);
            dispatch('recalculateBalanceAction', date.dateFormat)
        },
        async moveDateRightAction({commit, dispatch}, date) {
            const result = await Vue.resource('record/' + date.dateFormat).get();
            const data = await result.json();
            const payload = {
                data: data,
                moveLeft: false
            };
            commit('getNewRecordsMutation', payload);
            dispatch('recalculateBalanceAction', date.dateFormat)
        }
    }
})