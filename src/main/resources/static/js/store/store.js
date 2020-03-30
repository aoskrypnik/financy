import Vue from 'vue';
import VueResource from 'vue-resource'
import Vuex from 'vuex';

Vue.use(VueResource);
Vue.use(Vuex);

function sortRecords() {
    return function (a, b) {
        if (new Date(a.creationDate) - new Date(b.creationDate) === 0) {
            return -(a.id - b.id)
        }
        return -(new Date(a.creationDate) - new Date(b.creationDate))
    };
}

export default new Vuex.Store({
    state: {
        incomes: frontendData.incomes,
        expenses: frontendData.expenses,
        profile: frontendData.profile,
        balance: frontendData.balance,
        expenseCategories: [],
        incomeCategories: [],
        dateList: [],
        toBeExpanded: undefined,
        iconsMap: {
            'FoodExpense': 'restaurant_menu',
            'CafeExpense': 'local_cafe',
            'MedicineExpense': 'local_hospital',
            'ShoppingExpense': 'shopping_basket',
            'EntertainmentExpense': 'casino',
            'TransportExpense': 'directions_bus',
            'TaxiExpense': 'local_taxi',
            'SportExpense': 'fitness_center',
            'TripsExpense': 'flight_takeoff',
            'PhoneExpense': 'call',
            'PresentsExpense': 'local_florist',
            'BillsExpense': 'house',
            'HygieneExpense': 'bathtub',
            'PetsExpense': 'pets',
            'CarExpense': 'directions_car',
            'OtherExpense': 'remove_circle_outline',
            'DepositIncome': 'money',
            'SalaryIncome': 'work',
            'PrizeIncome': 'emoji_events',
            'BusinessIncome': 'attach_money',
            'OtherIncome': 'add_circle_outline'
        }
    },
    getters: {
        iconsMapGetter: state => {
            return state.iconsMap
        },
        sortedRecords: state => {
            let records = [];

            let incomes = state.incomes ? state.incomes : {};
            for (let key in incomes) {
                if (incomes[key] !== undefined) {
                    let record = {
                        'category': key,
                        'list': incomes[key].sort(sortRecords()),
                        'type': 'Income'
                    };
                    records.push(record);
                }
            }

            let expenses = state.expenses ? state.expenses : {};
            for (let key in expenses) {
                if (expenses[key] !== undefined) {
                    let record = {
                        'category': key,
                        'list': expenses[key].sort(sortRecords()),
                        'type': 'Expense'
                    };
                    records.push(record);
                }
            }
            return records
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
        },
        toBeExpandedGetter: state => {
            return state.toBeExpanded;
        }
    },
    mutations: {
        addIncomeMutation(state, income) {
            const givenCategory = income.category;
            for (let key in state.incomes) {
                if (givenCategory === key && state.incomes.hasOwnProperty(key)) {
                    state.incomes[key].push(income);
                    return
                }
            }
            state.toBeExpanded = income.id;
            Vue.set(state.incomes, givenCategory, [income]);
        },
        addExpenseMutation(state, expense) {
            const givenCategory = expense.category;
            for (let key in state.expenses) {
                if (expense.category === key && state.expenses.hasOwnProperty(key)) {
                    state.expenses[key].push(expense);
                    return
                }
            }
            state.toBeExpanded = expense.id;
            Vue.set(state.expenses, givenCategory, [expense]);
        },
        removeIncomeMutation(state, income) {
            let copy = state.incomes[income.category];
            const deletionIndex = copy.findIndex(item => item.id === income.id);
            if (deletionIndex > -1) {
                copy = [
                    ...copy.slice(0, deletionIndex),
                    ...copy.slice(deletionIndex + 1)
                ]
            }
            copy.length === 0
                ? Vue.set(state.incomes, income.category, undefined)
                : state.incomes[income.category] = copy;
        },
        removeExpenseMutation(state, expense) {
            let copy = state.expenses[expense.category];
            const deletionIndex = copy.findIndex(item => item.id === expense.id);
            if (deletionIndex > -1) {
                copy = [
                    ...copy.slice(0, deletionIndex),
                    ...copy.slice(deletionIndex + 1)
                ]
            }
            copy.length === 0
                ? Vue.set(state.expenses, expense.category, undefined)
                : state.expenses[expense.category] = copy;
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
        createDatesListMutation(state, payload) {
            const curDate = new Date(payload.curDate);
            let first = new Date(payload.data.first);
            first.setDate(2);
            let last = new Date(payload.data.last);
            last.setDate(2);
            state.dateList = [];
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
            if (payload.moveLeft !== undefined) {
                const curIndex = state.dateList.findIndex(e => e.isCurrent === true);
                state.dateList[curIndex].isCurrent = false;
                payload.moveLeft
                    ? state.dateList[curIndex - 1].isCurrent = true
                    : state.dateList[curIndex + 1].isCurrent = true
            }
        }
    },
    actions: {
        async addIncomeAction({state, commit, dispatch}, record) {
            const result = await Vue.resource('/income{/id}').save({}, record);
            const data = await result.json();
            const currentDate = state.dateList.find(e => e.isCurrent === true).dateFormat;
            const creationDate = data.creationDate;
            if (creationDate.split('-')[0] !== currentDate.split('-')[0] ||
                creationDate.split('-')[1] !== currentDate.split('-')[1]) {
                dispatch('createDatesListAction', creationDate);
                dispatch('justGetNewRecordsAction', creationDate);
            }
            commit('addIncomeMutation', data);
            dispatch('recalculateBalanceAction', creationDate)
        },
        async addExpenseAction({state, commit, dispatch}, record) {
            const result = await Vue.resource('/expense{/id}').save({}, record);
            const data = await result.json();
            const currentDate = state.dateList.find(e => e.isCurrent === true).dateFormat;
            const creationDate = data.creationDate;
            if (creationDate.split('-')[0] !== currentDate.split('-')[0] ||
                creationDate.split('-')[1] !== currentDate.split('-')[1]) {
                dispatch('createDatesListAction', creationDate);
                dispatch('justGetNewRecordsAction', creationDate);
            }
            commit('addExpenseMutation', data);
            dispatch('recalculateBalanceAction', creationDate)
        },
        async removeIncomeAction({commit, dispatch}, income) {
            const result = await Vue.resource('/income{/id}').remove({id: income.id});
            if (result.ok) {
                commit('removeIncomeMutation', income);
                dispatch('recalculateBalanceAction', income.creationDate)
            }
        },
        async removeExpenseAction({commit, dispatch}, expense) {
            const result = await Vue.resource('/expense{/id}').remove({id: expense.id});
            if (result.ok) {
                commit('removeExpenseMutation', expense);
                dispatch('recalculateBalanceAction', expense.creationDate)
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
        async createDatesListAction({commit}, date) {
            const result = await Vue.resource('/record/bounds').get();
            const data = await result.json();
            const payload = {
                data: data,
                curDate: date
            };
            commit('createDatesListMutation', payload)
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
        async justGetNewRecordsAction({commit, dispatch}, date) {
            const result = await Vue.resource('record/' + date).get();
            const data = await result.json();
            const payload = {
                data: data,
                moveLeft: undefined
            };
            commit('getNewRecordsMutation', payload);
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