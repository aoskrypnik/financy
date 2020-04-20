<template>
    <v-container>
        <v-layout justify-center>Incomes chart</v-layout>
        <GChart
                type="PieChart"
                :data="chartDataIncomes"
                :options="chartOptions"
        />
        <v-layout justify-center>Expenses chart</v-layout>
        <GChart
                type="PieChart"
                :data="chartDataExpenses"
                :options="chartOptions"
        />
    </v-container>
</template>

<script>
    import {GChart} from 'vue-google-charts'
    import {mapGetters} from "vuex";

    export default {
        components: {
            GChart
        },
        computed: {
            ...mapGetters(['sortedRecords'])
        },
        data() {
            const incomes = this.$store.getters.sortedRecords.filter(e => e.type === 'Income');
            const expenses = this.$store.getters.sortedRecords.filter(e => e.type === 'Expense');

            let incomesChart = [['Category', 'Sum']]
            for (let i = 0; i < incomes.length; i++) {
                incomesChart[i + 1] = [incomes[i].category,
                    incomes[i].list
                        .map(a => a.sum)
                        .reduce((a, b) => a + b, 0)]
            }
            let expensesChart = [['Category', 'Sum']]
            for (let i = 0; i < expenses.length; i++) {
                expensesChart[i + 1] = [expenses[i].category,
                    expenses[i].list
                        .map(a => a.sum)
                        .reduce((a, b) => a + b, 0)]
            }
            return {
                chartDataIncomes: incomesChart,
                chartDataExpenses: expensesChart,
                chartOptions: {
                    chart: {
                        title: 'Company Performance',
                        subtitle: 'Sales, Expenses, and Profit: 2014-2017',
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>