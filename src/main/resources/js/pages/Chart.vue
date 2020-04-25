<template>
    <v-container>
        <record-date></record-date>
        <v-layout justify-center>Incomes chart</v-layout>
        <GChart
                type="PieChart"
                :data="chartDataIncomes"
                :options="chartOptions"
                v-if="chartDataIncomes.length > 1"
        />
        <img v-else class="data-not-found" src="/assets/no_data.png">
        <v-layout justify-center>Expenses chart</v-layout>
        <GChart
                type="PieChart"
                :data="chartDataExpenses"
                :options="chartOptions"
                v-if="chartDataExpenses.length > 1"
        />
        <img v-else class="data-not-found" src="/assets/no_data.png">
    </v-container>
</template>

<script>
    import RecordDate from "../components/records/RecordDate.vue";
    import {GChart} from 'vue-google-charts'
    import {mapGetters} from "vuex";

    export default {
        components: {
            GChart,
            RecordDate
        },
        computed: {
            ...mapGetters(['sortedRecords']),
            chartDataIncomes() {
                const incomes = this.$store.getters.sortedRecords.filter(e => e.type === 'Income');
                let incomesChart = [['Category', 'Sum']]
                for (let i = 0; i < incomes.length; i++) {
                    incomesChart[i + 1] = [incomes[i].category,
                        incomes[i].list
                            .map(a => a.sum)
                            .reduce((a, b) => a + b, 0)]
                }
                return incomesChart;
            },
            chartDataExpenses() {
                const expenses = this.$store.getters.sortedRecords.filter(e => e.type === 'Expense');
                let expensesChart = [['Category', 'Sum']]
                for (let i = 0; i < expenses.length; i++) {
                    expensesChart[i + 1] = [expenses[i].category,
                        expenses[i].list
                            .map(a => a.sum)
                            .reduce((a, b) => a + b, 0)]
                }
                return expensesChart;
            }
        },
        data() {
            return {
                chartOptions: {
                    chart: {
                        title: 'Company Performance',
                        subtitle: 'Sales, Expenses, and Profit: 2014-2017',
                    }
                }
            }
        },
        created() {
            this.$store.dispatch('createDatesListAction', new Date());
        },
    }
</script>

<style scoped>
    .data-not-found {
        width: 150px;
        height: 150px;
        display: block;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 15px;
    }
</style>